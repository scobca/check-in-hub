package org.scobca.checkinhub.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("competitions")
data class Competition(
    @Id
    val id: Long? = null,

    @Column
    var name: String,

    @CreatedDate
    var createdAt: Instant?,

    @LastModifiedDate
    var updatedAt: Instant?
)
