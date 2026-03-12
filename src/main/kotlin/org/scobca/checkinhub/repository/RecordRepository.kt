package org.scobca.checkinhub.repository

import org.scobca.checkinhub.repository.specs.SpecsRecordRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RecordRepository : ReactiveCrudRepository<Record, Long>, SpecsRecordRepository