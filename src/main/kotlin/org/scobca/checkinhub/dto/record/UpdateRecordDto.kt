package org.scobca.checkinhub.dto.record

import org.scobca.checkinhub.enums.AgeCategory
import org.scobca.checkinhub.enums.CompetitionResult
import org.scobca.checkinhub.enums.Flows
import org.scobca.checkinhub.interfaces.DtoClass

data class UpdateRecordDto(
    val username: String?,
    val flow: Flows?,
    val competitionId: Long?,
    val ageCategory: AgeCategory?,
    val result: CompetitionResult?,
) : DtoClass
