package org.scobca.checkinhub.dto.filters

import org.scobca.checkinhub.interfaces.FilterDtoClass

data class RecordsFilters(
    val username: String?,
    val flow: String?,
    val competitionId: Long?,
    val competitionName: String?,
    val ageCategory: String?,
    val result: String?,
    val attendance: String?,
) : FilterDtoClass
