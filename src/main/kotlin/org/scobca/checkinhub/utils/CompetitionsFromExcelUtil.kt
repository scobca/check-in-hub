package org.scobca.checkinhub.utils

import org.apache.poi.ss.usermodel.WorkbookFactory
import org.scobca.checkinhub.dto.competition.CreateCompetitionDto
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class CompetitionsFromExcelUtil {

    fun parse(inputStream: InputStream): Set<CreateCompetitionDto> {

        return WorkbookFactory.create(inputStream).use { workbook ->
            val sheet = workbook.getSheetAt(0)
            sheet.drop(1)
                .mapNotNull { row ->
                    val name = row.getCell(2)?.toString()?.trim()
                    name?.takeIf { it.isNotBlank() }?.let {
                        CreateCompetitionDto(name = it)
                    }
                }
                .toSet()
        }
    }
}