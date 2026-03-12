package org.scobca.checkinhub.controller

import org.scobca.checkinhub.dto.competition.CompetitionNamesOutputDto
import org.scobca.checkinhub.dto.competition.CreateCompetitionDto
import org.scobca.checkinhub.dto.competition.UpdateCompetitionDto
import org.scobca.checkinhub.dto.filters.CompetitionFilters
import org.scobca.checkinhub.entity.Competition
import org.scobca.checkinhub.interfaces.ReactiveRestController
import org.scobca.checkinhub.io.BasicSuccessfulResponse
import org.scobca.checkinhub.service.CompetitionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/competitions")
class CompetitionsController(
    private val service: CompetitionService
) : ReactiveRestController<Long, Competition, CreateCompetitionDto, UpdateCompetitionDto, CompetitionFilters> {

    @GetMapping
    override fun getAll(
        pageable: Pageable,
        @ModelAttribute filter: CompetitionFilters,
    ): Mono<BasicSuccessfulResponse<Page<Competition>>> {
        return service.getAll(pageable, filter)
            .map { BasicSuccessfulResponse(it) }
    }

    @GetMapping("/{id}")
    override fun getById(@PathVariable id: Long): Mono<BasicSuccessfulResponse<Competition>> {
        return service.getById(id)
            .map { BasicSuccessfulResponse(it) }
    }

    @GetMapping("/getByName/{name}")
    fun getByName(@PathVariable name: String): Mono<BasicSuccessfulResponse<Competition>> {
        return service.getByName(name)
            .map { BasicSuccessfulResponse(it) }
    }

    @GetMapping("/getAllNames")
    fun getAllNames(): Mono<BasicSuccessfulResponse<CompetitionNamesOutputDto>> {
        return service.getCompetitionsNames()
            .map { BasicSuccessfulResponse(it) }
    }

    @PostMapping
    override fun create(@RequestBody body: CreateCompetitionDto): Mono<BasicSuccessfulResponse<Competition>> {
        return service.create(body)
            .map { BasicSuccessfulResponse(it) }
    }

    @PatchMapping("/{id}")
    override fun update(
        @PathVariable id: Long,
        @RequestBody body: UpdateCompetitionDto
    ): Mono<BasicSuccessfulResponse<Competition>> {
        return service.update(id, body)
            .map { BasicSuccessfulResponse(it) }
    }

    @DeleteMapping("/{id}")
    override fun deleteById(@PathVariable id: Long): Mono<BasicSuccessfulResponse<String>> {
        return service.deleteById(id)
            .map { BasicSuccessfulResponse(it) }
    }

    override fun deleteAll(): Mono<BasicSuccessfulResponse<String>> {
        return Mono.just(BasicSuccessfulResponse(""))
    }
}