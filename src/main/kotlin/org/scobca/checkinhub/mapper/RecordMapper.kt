package org.scobca.checkinhub.mapper

import org.mapstruct.Mapper
import org.scobca.checkinhub.dto.record.CreateRecordDto
import org.scobca.checkinhub.entity.Records
import org.scobca.checkinhub.enums.Attendance

@Mapper(componentModel = "spring")
fun interface RecordMapper {
    fun recordFromDto(dto: CreateRecordDto, attendance: Attendance): Records
}