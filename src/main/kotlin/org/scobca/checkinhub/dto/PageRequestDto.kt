package org.scobca.checkinhub.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.scobca.checkinhub.interfaces.DtoClass

data class PageRequestDto(
    @field:Min(0) val page: Int = 0,
    @field:Min(1) @field:Max(100) val size: Int = 20
) : DtoClass