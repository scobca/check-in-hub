package org.scobca.checkinhub.mapper

import org.mapstruct.Mapper
import org.scobca.checkinhub.dto.competition.CreateCompetitionDto
import org.scobca.checkinhub.entity.Competition

@Mapper(componentModel = "spring")
fun interface CompetitionMapper {
    fun competitionFromDto(dto: CreateCompetitionDto): Competition
}