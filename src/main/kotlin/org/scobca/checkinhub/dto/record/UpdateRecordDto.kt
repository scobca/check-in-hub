package org.scobca.checkinhub.dto.record

import org.scobca.checkinhub.enums.Flows
import org.scobca.checkinhub.interfaces.DtoClass

data class UpdateRecordDto(
    val username: String?,
    val flow: Flows?,
) : DtoClass
