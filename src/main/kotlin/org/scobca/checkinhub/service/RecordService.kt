package org.scobca.checkinhub.service

import org.scobca.checkinhub.dto.filters.RecordsFilters
import org.scobca.checkinhub.dto.record.CreateRecordDto
import org.scobca.checkinhub.dto.record.UpdateRecordDto
import org.scobca.checkinhub.entity.Records
import org.scobca.checkinhub.enums.Attendance
import org.scobca.checkinhub.exception.NotFoundException
import org.scobca.checkinhub.interfaces.ReactiveCrudService
import org.scobca.checkinhub.mapper.RecordMapper
import org.scobca.checkinhub.repository.RecordRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono

@Service
class RecordService(
    private val repository: RecordRepository,
    private val recordMapper: RecordMapper,
    private val competitionService: CompetitionService,
) : ReactiveCrudService<Long, Records, CreateRecordDto, UpdateRecordDto, RecordsFilters> {

    override fun getAll(
        pageable: Pageable,
        filters: RecordsFilters
    ): Mono<Page<Records>> {
        return repository.findAll(filters, pageable)
    }

    override fun getById(id: Long): Mono<Records> {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Запись с ID $id не существует.")))
    }

    @Transactional
    override fun create(item: CreateRecordDto): Mono<Records> {
        return competitionService.getById(item.competitionId)
            .switchIfEmpty(Mono.error(NotFoundException("Компетенция с ID ${item.competitionId} не найдена.")))
            .flatMap { repository.save(recordMapper.recordFromDto(item, Attendance.NOT_STATED)) }
    }

    @Transactional
    fun createBatch(items: Collection<CreateRecordDto>): Mono<List<Records>> {
        if (items.isEmpty()) return Mono.just(emptyList())
        val recordsToSave = items.map { recordMapper.recordFromDto(it, attendance = Attendance.NOT_STATED) }

        return repository.saveAll(recordsToSave)
            .collectList()
    }

    @Transactional
    override fun update(
        id: Long,
        item: UpdateRecordDto
    ): Mono<Records> {
        return getById(id)
            .switchIfEmpty(Mono.error(NotFoundException("Запись с ID $id не найдена.")))
            .zipWith(item.toMono()) { record, dto ->
                dto.username?.let { record.username = it }
                dto.flow?.let { record.flow = it }

                return@zipWith record
            }
    }

    @Transactional
    override fun deleteById(id: Long): Mono<String> {
        return getById(id)
            .switchIfEmpty { Mono.error(NotFoundException("Записи с таким ID нет.")) }
            .mapNotNull(repository::delete)
            .thenReturn("Запись успешно удалена.")
    }

    @Transactional
    override fun deleteAll(): Mono<String> {
        return repository.deleteAll()
            .thenReturn("Все записи успешно удалены.")
    }
}