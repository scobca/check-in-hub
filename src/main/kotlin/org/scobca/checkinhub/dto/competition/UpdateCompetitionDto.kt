package org.scobca.checkinhub.dto.competition

import org.scobca.checkinhub.interfaces.DtoClass

data class UpdateCompetitionDto(
    val id: Long,
    val name: String?,
) : DtoClass
