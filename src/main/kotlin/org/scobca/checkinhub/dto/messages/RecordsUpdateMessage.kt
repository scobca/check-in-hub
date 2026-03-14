package org.scobca.checkinhub.dto.messages

import kotlinx.serialization.Serializable
import org.scobca.checkinhub.enums.Attendance
import org.scobca.checkinhub.interfaces.DtoClass

@Serializable
data class RecordsUpdateMessage(
    val id: Long,
    val status: Attendance,
) : DtoClass
