package org.scobca.checkinhub.interfaces

import org.scobca.checkinhub.io.BasicSuccessfulResponse

interface ConvertableToHttpResponse<T> {
    fun T.toHttpResponse(): BasicSuccessfulResponse<T> {
        return BasicSuccessfulResponse(this)
    }
}