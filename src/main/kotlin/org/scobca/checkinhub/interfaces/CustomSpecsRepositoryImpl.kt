package org.scobca.checkinhub.interfaces

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Query

interface CustomSpecsRepositoryImpl<T, Filter> {
    val template: R2dbcEntityTemplate

    fun createQuery(specification: Filter): Query
}