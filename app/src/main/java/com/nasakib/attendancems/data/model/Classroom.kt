package com.nasakib.attendancems.data.model

data class Classroom(
    val id: Int = 0,
    val course_id: Int,
    val course: Course? = null,
    val session_id: Int,
    val session: Session? = null,
    val name: String,
    val created_at: String = "",
    val updated_at: String = "",
)
