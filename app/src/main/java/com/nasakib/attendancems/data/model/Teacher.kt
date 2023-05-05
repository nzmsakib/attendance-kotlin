package com.nasakib.attendancems.data.model

data class Teacher(
    val id: Int,
    val user_id: Int,
    val user: User? = null,
    val department_id: Int,
    val department: Department? = null,
    val designation: String,
    val phone: String,
    val address: String,
    val nickname: String,
    val created_at: String,
    val updated_at: String
)
