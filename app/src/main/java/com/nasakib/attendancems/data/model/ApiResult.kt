package com.nasakib.attendancems.data.model

data class ApiResult<T>(
    val status: String,
    val message: String = "",
    val data: List<T> = emptyList()
)
