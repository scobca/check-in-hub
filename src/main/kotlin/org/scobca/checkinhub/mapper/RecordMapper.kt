package org.scobca.checkinhub.mapper

import org.mapstruct.Mapper
import org.scobca.checkinhub.dto.record.CreateRecordDto

@Mapper(componentModel = "spring")
fun interface RecordMapper {
    fun recordFromDto(dto: CreateRecordDto): Record
}