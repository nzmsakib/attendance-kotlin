package com.nasakib.attendancems.data.model

data class Classroom(
    val id: Int,
    val course_id: Int,
    val session_id: Int,
    val name: String,
    val created_at: String,
    val updated_at: String,
)
