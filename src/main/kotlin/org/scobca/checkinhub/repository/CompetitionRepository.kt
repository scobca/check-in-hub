package org.scobca.checkinhub.repository

import org.scobca.checkinhub.entity.Competition
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface CompetitionRepository : ReactiveCrudRepository<Competition, Long> {
    fun findAll(pageable: Pageable): Flux<Competition>
    fun findByName(name: String): Mono<Competition>
    fun findCompetitionsNames(): Flux<String>
}