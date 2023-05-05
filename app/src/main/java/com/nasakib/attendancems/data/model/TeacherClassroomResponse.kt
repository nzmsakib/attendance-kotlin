package com.nasakib.attendancems.data.model

data class TeacherClassroomResponse(
    val attendances: List<Attendance> = emptyList(),
)
