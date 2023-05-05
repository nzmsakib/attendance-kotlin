package com.nasakib.attendancems.data.model

data class Student(
    val id: Int,
    val user_id: Int,
    val user: User? = null,
    val department_id: Int,
    val department: Department? = null,
    val session_id: Int,
    val session: Session? = null,
    val roll: Int,
    val created_at: String,
    val updated_at: String
)
