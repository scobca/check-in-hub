package org.scobca.checkinhub.entity

import org.scobca.checkinhub.enums.AgeCategory
import org.scobca.checkinhub.enums.Attendance
import org.scobca.checkinhub.enums.CompetitionResult
import org.scobca.checkinhub.enums.Flows
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("records")
data class Records(
    @Id
    val id: Long? = null,

    @Column
    var username: String,

    @Column
    var flow: Flows,

    @Column("competition_id")
    val competitionId: Long,

    @Column
    val ageCategory: AgeCategory,

    @Column
    val result: CompetitionResult,

    @Column
    var attendance: Attendance,

    @CreatedDate
    var createdAt: Instant?,

    @LastModifiedDate
    var updatedAt: Instant?,
)
