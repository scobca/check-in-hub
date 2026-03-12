package org.scobca.checkinhub.repository.specs

import org.scobca.checkinhub.dto.filters.CompetitionFilters
import org.scobca.checkinhub.entity.Competition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

fun interface SpecsCompetitionRepository {
    fun findAll(specification: CompetitionFilters, pageable: Pageable): Mono<Page<Competition>>
}