package org.scobca.checkinhub.repository.specs.generated

import org.scobca.checkinhub.dto.filters.RecordsFilters
import org.scobca.checkinhub.entity.Records
import org.scobca.checkinhub.interfaces.CustomSpecsRepositoryImpl
import org.scobca.checkinhub.repository.specs.SpecsRecordRepository
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
class SpecsRecordRepositoryImpl(
    override val template: R2dbcEntityTemplate
) : SpecsRecordRepository, CustomSpecsRepositoryImpl<Records, RecordsFilters> {

    override fun findAll(
        specification: RecordsFilters,
        pageable: Pageable
    ): Mono<Page<Records>> {
        val query = createQuery(specification)
            .sort(pageable.sort)
            .limit(pageable.pageSize)
            .offset((pageable.pageNumber * pageable.pageSize).toLong())

        val data = template.select(query, Records::class.java).collectList()
        val count = template.count(query, Records::class.java)

        return Mono.zip(data, count)
            .map { (items, total) ->
                PageImpl(items, pageable, total.toLong())
            }
    }

    override fun createQuery(specification: RecordsFilters): Query {
        val criteriaList = mutableListOf<Criteria>()

        specification.username
            ?.takeIf { it.isNotBlank() }
            ?.let { username ->
                criteriaList.add(Criteria.where("username").like("%$username%"))
            }

        specification.flow
            ?.let { flow ->
                criteriaList.add(Criteria.where("flow").`is`(flow.name))
            }

        specification.competitionId
            ?.let { competitionId ->
                criteriaList.add(Criteria.where("competition_id").`is`(competitionId))
            }

        specification.ageCategory
            ?.let { ageCategory ->
                criteriaList.add(Criteria.where("age_category").`is`(ageCategory.name))
            }

        specification.result
            ?.let { result ->
                criteriaList.add(Criteria.where("result").`is`(result.name))
            }

        val criteria = if (criteriaList.isNotEmpty()) {
            criteriaList.reduce { acc, next -> acc.and(next) }
        } else {
            Criteria.empty()
        }

        return Query.query(criteria)
    }
}