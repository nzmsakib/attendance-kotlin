package com.nasakib.attendancems.data.model

data class Attendance(
    val id: Int = 0,
    val classroom_id: Int = 0,
    val classroom: Classroom? = null,
    val teacher_id: Int = 0,
    val teacher: Teacher? = null,
    val name: String = "",
    val score: Float = 0.0f,
    val created_at: String = "",
    val updated_at: String = "",
)
