package org.scobca.checkinhub.utils

import org.apache.poi.ss.usermodel.WorkbookFactory
import org.scobca.checkinhub.dto.record.CreateRecordDto
import org.scobca.checkinhub.enums.AgeCategory
import org.scobca.checkinhub.enums.CompetitionResult
import org.scobca.checkinhub.enums.Flows
import org.scobca.checkinhub.exception.BadRequestException
import org.scobca.checkinhub.repository.CompetitionRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.io.InputStream

@Component
class RecordsFromExcelUtil(private val competitionRepository: CompetitionRepository) {

    fun parse(inputStream: InputStream): Mono<List<CreateRecordDto>> {
        return competitionRepository.findAll()
            .collectMap({ it.name.trim() }, { it.id ?: 0L })
            .map { competitions ->
                WorkbookFactory.create(inputStream).use { workbook ->
                    val sheet = workbook.getSheetAt(0)

                    sheet.drop(1)
                        .mapNotNull { row ->
                            try {
                                val competitionName = row.getCell(2)?.toString()?.trim()
                                val competitionId = competitions[competitionName]

                                if (competitionId == null || competitionId == 0L) {
                                    println("Компетенция не найдена: '$competitionName' в строке ${row.rowNum}")
                                    return@mapNotNull null
                                }

                                CreateRecordDto(
                                    flow = Flows.fromAlias(row.getCell(0)?.toString() ?: "")
                                        ?: throw BadRequestException("Неверный flow: ${row.getCell(0)}"),
                                    username = row.getCell(1)?.toString() ?: "",

                                    competitionId = competitions[row.getCell(2)?.toString()?.trim()] ?: throw BadRequestException("Ошибка в компетенции ${row.getCell(2)?.toString()}"),

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

}