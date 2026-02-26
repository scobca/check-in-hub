package org.scobca.checkinhub.interfaces

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.relational.core.query.Criteria
import reactor.core.publisher.Mono

interface ReactiveCrudService<ID: Any, RES: Any, CREATE: Any, UPDATE: Any> {
    fun getAll(pageable: Pageable, criteria: Criteria?): Mono<Page<RES>>

    fun getById(id: ID): Mono<RES>

    fun create(item: CREATE): Mono<RES>

    fun update(id: ID, item: UPDATE): Mono<RES>

    fun deleteById(id: ID): Mono<*>

    fun deleteAll(): Mono<*>
}