package com.evolutivecode.opccadminportal.core.domain

class ResponseModel<E>() {
    var totalPages: Int = 0
    var totalElements: Long = 0
    var content: List<E>? = emptyList()
}