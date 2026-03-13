package org.scobca.checkinhub.utils

import kotlinx.coroutines.runBlocking
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.scobca.checkinhub.dto.record.CreateRecordDto
import org.scobca.checkinhub.enums.AgeCategory
import org.scobca.checkinhub.enums.CompetitionResult
import org.scobca.checkinhub.enums.Flows
import org.scobca.checkinhub.exception.BadRequestException
import org.scobca.checkinhub.repository.CompetitionRepository
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class RecordsFromExcelUtil(private val competitionRepository: CompetitionRepository) {

    fun parse(inputStream: InputStream): List<CreateRecordDto> {
        val competitions = runBlocking {
            competitionRepository.findAll()
                .collectMap({ it.name }, { it.id ?: 0L })
                .block() ?: emptyMap()
        }

        WorkbookFactory.create(inputStream).use { workbook ->
            val sheet = workbook.getSheetAt(0)

            return sheet.drop(1)
                .mapNotNull { row ->
                    try {
                        CreateRecordDto(
                            flow = Flows.fromAlias(row.getCell(0)?.toString() ?: "")
                                ?: throw BadRequestException("Неверный flow: ${row.getCell(0)}"),
                            username = row.getCell(1)?.toString() ?: "",

                            competitionId = competitions[row.getCell(2)?.toString()] ?: 0L,

                            ageCategory = AgeCategory.fromAlias(row.getCell(3)?.toString() ?: "")
                                ?: throw BadRequestException("Неверная категория: ${row.getCell(3)}"),

                            result = CompetitionResult.fromAlias(row.getCell(4)?.toString() ?: "")
                                ?: throw BadRequestException("Неверный результат: ${row.getCell(4)}")
                        )
                    } catch (e: Exception) {
                        println("Ошибка в строке ${row.rowNum}: ${e.message}")
                        null
                    }
                }
        }
    }

}