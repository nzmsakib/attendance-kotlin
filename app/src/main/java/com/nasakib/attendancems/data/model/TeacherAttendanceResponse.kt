package com.nasakib.attendancems.data.model

data class TeacherAttendanceResponse(
    val students: List<Student> = listOf(),
    val students_active: List<Int> = listOf(),
)
