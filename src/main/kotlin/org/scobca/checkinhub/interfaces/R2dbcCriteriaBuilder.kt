package org.scobca.checkinhub.interfaces

import org.springframework.data.relational.core.query.Criteria

fun interface R2dbcCriteriaBuilder<T : FilterDtoClass> {
    fun buildCriteria(filter: T): Criteria
}