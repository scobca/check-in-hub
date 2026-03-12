package org.scobca.checkinhub.repository

import org.scobca.checkinhub.entity.Competition
import org.scobca.checkinhub.repository.specs.SpecsCompetitionRepository
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface CompetitionRepository : ReactiveCrudRepository<Competition, Long>, SpecsCompetitionRepository {
    fun findByName(name: String): Mono<Competition>

    @Query("SELECT name FROM competitions")
    fun findCompetitionsNames(): Flux<String>
}