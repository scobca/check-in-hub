package org.scobca.checkinhub.dto.competition

import org.scobca.checkinhub.interfaces.DtoClass

data class CreateCompetitionDto(
    val name: String,
) : DtoClass
