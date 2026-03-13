package org.scobca.checkinhub.utils

import org.apache.poi.ss.usermodel.WorkbookFactory
import org.scobca.checkinhub.dto.competition.CreateCompetitionDto
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class CompetitionsFromExcelUtil {

    fun parse(inputStream: InputStream): List<CreateCompetitionDto> {

        WorkbookFactory.create(inputStream).use { workbook ->
            val sheet = workbook.getSheetAt(0)

            return sheet.drop(1)
                .mapNotNull { row ->
                    CreateCompetitionDto(
                        name = row.getCell(3)?.toString() ?: ""
                    )
                }
        }
    }
}