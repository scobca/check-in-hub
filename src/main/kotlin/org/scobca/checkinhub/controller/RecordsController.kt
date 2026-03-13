package org.scobca.checkinhub.controller

import org.scobca.checkinhub.dto.filters.RecordsFilters
import org.scobca.checkinhub.dto.record.CreateRecordDto
import org.scobca.checkinhub.dto.record.UpdateRecordDto
import org.scobca.checkinhub.entity.Records
import org.scobca.checkinhub.interfaces.ReactiveRestController
import org.scobca.checkinhub.io.BasicSuccessfulResponse
import org.scobca.checkinhub.service.RecordService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/records")
class RecordsController(
    private val service: RecordService
) : ReactiveRestController<Long, Records, CreateRecordDto, UpdateRecordDto, RecordsFilters> {

    @GetMapping
    override fun getAll(
        pageable: Pageable,
        filter: RecordsFilters
    ): Mono<BasicSuccessfulResponse<Page<Records>>> {
        return service.getAll(pageable, filter)
            .map { BasicSuccessfulResponse(it) }
    }

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: Long): Mono<BasicSuccessfulResponse<Records>> {
        return service.getById(id)
            .map { BasicSuccessfulResponse(it) }
    }

    @PostMapping
    override fun create(@RequestBody body: CreateRecordDto): Mono<BasicSuccessfulResponse<Records>> {
        return service.create(body)
            .map { BasicSuccessfulResponse(it) }
    }

    @PatchMapping("/{id}")
    override fun update(
        @PathVariable id: Long,
        @RequestBody body: UpdateRecordDto
    ): Mono<BasicSuccessfulResponse<Records>> {
        return service.update(id, body)
            .map { BasicSuccessfulResponse(it) }
    }

    @DeleteMapping("/{id}")
    override fun deleteById(@PathVariable id: Long): Mono<BasicSuccessfulResponse<String>> {
        return service.deleteById(id)
            .map { BasicSuccessfulResponse(it) }
    }

    @DeleteMapping
    override fun deleteAll(): Mono<BasicSuccessfulResponse<String>> {
        return service.deleteAll()
            .map { BasicSuccessfulResponse(it) }
    }
}