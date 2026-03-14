package org.scobca.checkinhub.repository.specs.generated

import org.scobca.checkinhub.dto.filters.CompetitionFilters
import org.scobca.checkinhub.entity.Competition
import org.scobca.checkinhub.interfaces.CustomSpecsRepositoryImpl
import org.scobca.checkinhub.repository.specs.SpecsCompetitionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2

@Component
class SpecsCompetitionRepositoryImpl(
    override val template: R2dbcEntityTemplate
) : SpecsCompetitionRepository, CustomSpecsRepositoryImpl<Competition, CompetitionFilters> {

    override fun findAll(
        specification: CompetitionFilters,
        pageable: Pageable
    ): Mono<Page<Competition>> {
        val query = createQuery(specification)
            .sort(pageable.sort)
            .limit(pageable.pageSize)
            .offset((pageable.pageNumber * pageable.pageSize).toLong())

        val data = template.select(query, Competition::class.java).collectList()
        val count = template.count(query, Competition::class.java)

        return Mono.zip(data, count)
            .map { (items, total) ->
                PageImpl(items, pageable, total.toLong())
            }
    }

    override fun createQuery(specification: CompetitionFilters): Query {
        val criteriaList = mutableListOf<Criteria>()

        specification.name
            ?.takeIf { it.isNotBlank() }
            ?.let { name ->
                criteriaList.add(Criteria
                    .where("name")
                    .like("%$name%")
                    .ignoreCase(true)
                )
            }

        val criteria = if (criteriaList.isNotEmpty()) {
            criteriaList.reduce { acc, next -> acc.and(next) }
        } else {
            Criteria.empty()
        }

        return Query.query(criteria)
    }
}