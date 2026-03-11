package org.scobca.checkinhub.interfaces

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface ReactiveCrudService<ID: Any, RES: Any, CREATE: Any, UPDATE: Any> {
    fun getAll(pageable: Pageable): Mono<Page<RES>>

    fun getById(id: Mono<ID>): Mono<RES>

    fun create(item: Mono<CREATE>): Mono<RES>

    fun update(id: Mono<ID>, item: Mono<UPDATE>): Mono<RES>

    fun deleteById(id: Mono<ID>): Mono<*>

    fun deleteAll(): Mono<*>
}