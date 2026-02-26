package org.scobca.checkinhub.interfaces

interface AbstractResponse<T> {
    val status: Int
    val message: T?
}