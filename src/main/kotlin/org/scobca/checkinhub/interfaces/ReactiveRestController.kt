package org.scobca.checkinhub.interfaces

import org.scobca.checkinhub.io.BasicSuccessfulResponse
import org.scobca.checkinhub.dto.PageRequestDto
import org.springframework.data.domain.Page
import reactor.core.publisher.Mono

interface ReactiveRestController<ID: Any, RES: Any, CREATE: Any, UPDATE: Any> {

    fun getAll(req: PageRequestDto): Mono<BasicSuccessfulResponse<Page<RES>>>

    fun getById(id: ID): Mono<BasicSuccessfulResponse<RES>>

    fun create(body: CREATE): Mono<BasicSuccessfulResponse<RES>>

    fun update(
        id: ID,
        body: UPDATE
    ): Mono<BasicSuccessfulResponse<RES>>

    fun deleteById(id: ID): Mono<BasicSuccessfulResponse<String>>

    fun deleteAll(): Mono<BasicSuccessfulResponse<String>>
}