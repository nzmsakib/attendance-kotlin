package com.nasakib.attendancems.ui.teacher.attendance

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nasakib.attendancems.data.model.Attendance
import com.nasakib.attendancems.data.model.Student
import com.nasakib.attendancems.data.model.StudentAttendance

class AttendanceViewModel {
    private val _attendances = MutableLiveData<List<StudentAttendance>>()
    val attendances: LiveData<List<StudentAttendance>> = _attendances

    fun setStudentAttendances(attendances: List<StudentAttendance>) {
        _attendances.postValue(attendances)
        Log.d(this.javaClass.name, "setStudentAttendances: $attendances")
    }

    fun setAllPresent() {
        val studentAttendances = _attendances.value ?: return
        val newStudentAttendances = studentAttendances.map {
            StudentAttendance(it.student, true)
        }
        _attendances.postValue(newStudentAttendances)
    }

    fun setAllAbsent() {
        val studentAttendances = _attendances.value ?: return
        val newStudentAttendances = studentAttendances.map {
            StudentAttendance(it.student, false)
        }
        _attendances.postValue(newStudentAttendances)
    }
}