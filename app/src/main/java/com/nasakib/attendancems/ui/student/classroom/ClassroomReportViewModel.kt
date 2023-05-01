package com.nasakib.attendancems.ui.student.classroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nasakib.attendancems.data.model.StudentClassroomReport

class ClassroomReportViewModel {
    private val _reports = MutableLiveData<List<StudentClassroomReport>>(listOf())
    val reports: LiveData<List<StudentClassroomReport>> = _reports

    fun getReports() : List<StudentClassroomReport> {
        return _reports.value!!
    }

    fun setReports(reports: List<StudentClassroomReport>) {
        _reports.postValue(reports)
    }
}