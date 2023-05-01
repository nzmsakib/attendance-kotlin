package com.nasakib.attendancems.data.model

data class StudentDashboardReport(
    val course: Course,
    val classroom: Classroom,
    val attendancePercentage: Float,
)
