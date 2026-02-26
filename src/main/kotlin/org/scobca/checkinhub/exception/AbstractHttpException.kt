package org.scobca.checkinhub.exception

abstract class AbstractHttpException(val status: Int, override val message: String?) : RuntimeException(message)