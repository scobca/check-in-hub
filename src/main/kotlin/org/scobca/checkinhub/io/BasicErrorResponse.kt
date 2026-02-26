package org.scobca.checkinhub.io

import org.scobca.checkinhub.interfaces.AbstractResponse
import kotlinx.serialization.Serializable

@Serializable
data class BasicErrorResponse(override val status: Int, override val message: String) : AbstractResponse<String>
