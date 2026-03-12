package org.scobca.checkinhub.interfaces

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface ReactiveCrudService<ID: Any, RES: Any, CREATE: Any, UPDATE: Any, FILTER: FilterDtoClass> {
    fun getAll(pageable: Pageable, filters: FILTER): Mono<Page<RES>>

    fun getById(id: ID): Mono<RES>

    fun create(item: CREATE): Mono<RES>

    fun update(id: ID, item: UPDATE): Mono<RES>

    fun deleteById(id: ID): Mono<*>

    fun deleteAll(): Mono<*>
}