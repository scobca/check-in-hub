package org.scobca.checkinhub.interfaces

import org.scobca.checkinhub.io.BasicSuccessfulResponse
import jakarta.validation.Valid
import org.scobca.checkinhub.dto.PageRequestDto
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

interface ReactiveRestController<ID: Any, RES: Any, CREATE: Any, UPDATE: Any, FILTER : FilterDtoClass> {

    @GetMapping
    fun getAll(
        filters: FILTER?,
        @Valid @ModelAttribute req: PageRequestDto
    ): Mono<BasicSuccessfulResponse<Page<RES>>>

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: ID
    ): Mono<BasicSuccessfulResponse<RES>>

    @PostMapping
    fun create(
        @RequestBody body: CREATE
    ): Mono<BasicSuccessfulResponse<RES>>

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: ID,
        @RequestBody body: UPDATE
    ): Mono<BasicSuccessfulResponse<RES>>

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: ID
    ): Mono<BasicSuccessfulResponse<*>>

    @DeleteMapping
    fun deleteAll(): Mono<BasicSuccessfulResponse<*>>
}