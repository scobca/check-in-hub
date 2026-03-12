package org.scobca.checkinhub.repository.specs

import org.scobca.checkinhub.dto.filters.RecordsFilters
import org.scobca.checkinhub.entity.Records
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

fun interface SpecsRecordRepository {
    fun findAll(specification: RecordsFilters, pageable: Pageable): Mono<Page<Records>>
}