package com.nasakib.attendancems.data.model

data class Course(
    val id: Int,
    val department_id: Int,
    val department: Department? = null,
    val semester_id: Int,
    val title: String,
    val code: String,
    val credit: Float,
    val type: String,
    val created_at: String,
    val updated_at: String,
)
