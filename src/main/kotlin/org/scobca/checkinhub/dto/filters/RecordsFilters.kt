package org.scobca.checkinhub.dto.filters

import org.scobca.checkinhub.enums.AgeCategory
import org.scobca.checkinhub.enums.CompetitionResult
import org.scobca.checkinhub.enums.Flows
import org.scobca.checkinhub.interfaces.FilterDtoClass

data class RecordsFilters(
    val username: String?,
    val flow: Flows?,
    val competitionId: Long?,
    val ageCategory: AgeCategory?,
    val result: CompetitionResult?,
) : FilterDtoClass
