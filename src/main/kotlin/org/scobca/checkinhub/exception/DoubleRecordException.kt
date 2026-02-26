package org.scobca.checkinhub.exception

import org.springframework.http.HttpStatus

class DoubleRecordException(override val message: String) : AbstractHttpException(HttpStatus.CONFLICT.value(), message)