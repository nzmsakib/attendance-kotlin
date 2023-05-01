package com.nasakib.attendancems.data.model

data class Attendance(
    val id: Int,
    val classroom_id: Int,
    val teacher_id: Int,
    val name: String,
    val score: Float,
    val created_at: String,
    val updated_at: String,
)
