package org.scobca.checkinhub.mapper

import org.mapstruct.Mapper
import org.scobca.checkinhub.dto.record.CreateRecordDto
import org.scobca.checkinhub.entity.Records

@Mapper(componentModel = "spring")
fun interface RecordMapper {
    fun recordFromDto(dto: CreateRecordDto): Records
}