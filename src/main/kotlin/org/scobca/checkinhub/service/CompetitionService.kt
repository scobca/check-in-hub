package org.scobca.checkinhub.service

import org.scobca.checkinhub.dto.competition.CompetitionNamesOutputDto
import org.scobca.checkinhub.dto.competition.CreateCompetitionDto
import org.scobca.checkinhub.dto.competition.UpdateCompetitionDto
import org.scobca.checkinhub.entity.Competition
import org.scobca.checkinhub.exception.DoubleRecordException
import org.scobca.checkinhub.exception.NotFoundException
import org.scobca.checkinhub.interfaces.ReactiveCrudService
import org.scobca.checkinhub.mapper.CompetitionMapper
import org.scobca.checkinhub.repository.CompetitionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Instant

@Service
class CompetitionService(
    private val repository: CompetitionRepository,
    private val competitionMapper: CompetitionMapper,
) : ReactiveCrudService<Long, Competition, CreateCompetitionDto, UpdateCompetitionDto> {

    override fun getAll(
        pageable: Pageable,
    ): Mono<Page<Competition>> {
        return repository.findAll(pageable)
            .collectList()
            .zipWith(repository.count())
            .map { page -> PageImpl(page.t1, pageable, page.t2) }
    }

    override fun getById(id: Mono<Long>): Mono<Competition> {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Компетенции с таким ID $id нет.")))
    }

    fun getByName(name: Mono<String>): Mono<Competition> {
        return name
            .flatMap { name ->
                repository.findByName(name)
                    .switchIfEmpty(Mono.error(NotFoundException("Компетенции с таким именем '$name' нет.")))
            }
    }

    fun getCompetitionsNames(): Mono<CompetitionNamesOutputDto> {
        return repository.findCompetitionsNames()
            .collectList()
            .zipWith(repository.count())
            .map { res -> CompetitionNamesOutputDto(res.t1, res.t2) }
    }

    override fun create(item: Mono<CreateCompetitionDto>): Mono<Competition> {
        return item
            .flatMap { dto ->
                repository.findByName(dto.name)
                    .hasElement()
                    .flatMap { exists ->
                        if (exists) {
                            Mono.error(DoubleRecordException("Компетенция '${dto.name}' уже существует."))
                        } else {
                            repository.save(competitionMapper.competitionFromDto(dto))
                        }
                    }
            }
    }

    override fun update(
        id: Mono<Long>,
        item: Mono<UpdateCompetitionDto>
    ): Mono<Competition> {
        return getById(id)
            .switchIfEmpty { Mono.error(NotFoundException("Компетенции с таким ID нет.")) }
            .zipWith(item) { competition, dto ->
                dto.name?.let {
                    competition.name = it
                    competition.updatedAt = Instant.now()
                }
                competition
            }
            .flatMap(repository::save)
    }

    override fun deleteById(id: Mono<Long>): Mono<String> {
        return getById(id)
            .switchIfEmpty { Mono.error(NotFoundException("Компетенции с таким ID нет.")) }
            .mapNotNull(repository::delete)
            .thenReturn("Компетенция успешно удалена.")
    }

    override fun deleteAll(): Mono<String> {
        return repository.deleteAll()
            .thenReturn("Все компетенции успешно удалены.")
    }

}