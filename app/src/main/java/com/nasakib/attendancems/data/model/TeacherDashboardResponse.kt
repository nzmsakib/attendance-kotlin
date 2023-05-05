package com.nasakib.attendancems.data.model

data class TeacherDashboardResponse(
    val teacher: Teacher,
    val classrooms: List<Classroom>,
    val courses: List<Course>,
    val sessions: List<Session>
)
