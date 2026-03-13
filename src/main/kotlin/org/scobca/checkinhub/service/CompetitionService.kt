package org.scobca.checkinhub.service

import org.scobca.checkinhub.dto.competition.CompetitionNamesOutputDto
import org.scobca.checkinhub.dto.competition.CreateCompetitionDto
import org.scobca.checkinhub.dto.competition.UpdateCompetitionDto
import org.scobca.checkinhub.dto.filters.CompetitionFilters
import org.scobca.checkinhub.entity.Competition
import org.scobca.checkinhub.exception.DoubleRecordException
import org.scobca.checkinhub.exception.NotFoundException
import org.scobca.checkinhub.interfaces.ReactiveCrudService
import org.scobca.checkinhub.mapper.CompetitionMapper
import org.scobca.checkinhub.repository.CompetitionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono
import java.time.Instant

@Service
class CompetitionService(
    private val repository: CompetitionRepository,
    private val competitionMapper: CompetitionMapper,
) : ReactiveCrudService<Long, Competition, CreateCompetitionDto, UpdateCompetitionDto, CompetitionFilters> {

    override fun getAll(
        pageable: Pageable,
        filters: CompetitionFilters,
    ): Mono<Page<Competition>> {
        return repository.findAll(filters, pageable)
    }

    override fun getById(id: Long): Mono<Competition> {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Компетенции с таким ID $id нет.")))
    }

    fun getByName(name: String): Mono<Competition> {
        return repository.findByName(name)
            .switchIfEmpty(Mono.error(NotFoundException("Компетенции с таким именем '$name' нет.")))
    }

    fun getCompetitionsNames(): Mono<CompetitionNamesOutputDto> {
        return repository.findCompetitionsNames()
            .collectList()
            .zipWith(repository.count())
            .map { res -> CompetitionNamesOutputDto(res.t1, res.t2) }
    }

    @Transactional
    override fun create(item: CreateCompetitionDto): Mono<Competition> {
        return repository.findByName(item.name)
            .hasElement()
            .flatMap { exists ->
                if (exists) {
                    Mono.error(DoubleRecordException("Компетенция '${item.name}' уже существует."))
                } else {
                    repository.save(competitionMapper.competitionFromDto(item))
                }
            }
    }

    @Transactional
    fun createBatch(items: List<CreateCompetitionDto>): Mono<List<Competition>> {
        if (items.isEmpty()) return Mono.just(emptyList())
        val competitionsToSave = items.map { competitionMapper.competitionFromDto(it) }

        return repository.findAllByNameIn(items.map { it.name })
            .collectList()
            .flatMap { existingNames ->
                val existingNamesSet = existingNames.map { it.name }.toSet()
                val duplicates = items.filter { it.name in existingNamesSet }

                if (duplicates.isNotEmpty()) {
                    val duplicateNames = duplicates.joinToString(", ") { "'${it.name}'" }
                    return@flatMap Mono.error(DoubleRecordException("Компетенции уже существуют: $duplicateNames"))
                }

                repository.saveAll(competitionsToSave)
                    .collectList()
            }
    }


    @Transactional
    override fun update(
        id: Long,
        item: UpdateCompetitionDto
    ): Mono<Competition> {
        return getById(id)
            .switchIfEmpty { Mono.error(NotFoundException("Компетенции с таким ID нет.")) }
            .zipWith(item.toMono()) { competition, dto ->
                dto.name?.let {
                    competition.name = it
                    competition.updatedAt = Instant.now()
                }
                competition
            }
            .flatMap(repository::save)
    }

    @Transactional
    override fun deleteById(id: Long): Mono<String> {
        return getById(id)
            .switchIfEmpty { Mono.error(NotFoundException("Компетенции с таким ID нет.")) }
            .mapNotNull(repository::delete)
            .thenReturn("Компетенция успешно удалена.")
    }

    @Transactional
    override fun deleteAll(): Mono<String> {
        return repository.deleteAll()
            .thenReturn("Все компетенции успешно удалены.")
    }

}