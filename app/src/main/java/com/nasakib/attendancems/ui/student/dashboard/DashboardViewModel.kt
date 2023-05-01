package com.nasakib.attendancems.ui.student.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nasakib.attendancems.data.model.StudentDashboardReport

class DashboardViewModel {
    private val _reports = MutableLiveData<List<StudentDashboardReport>>()
    val reports: LiveData<List<StudentDashboardReport>> = _reports

    fun getReports() : List<StudentDashboardReport> {
        return _reports.value!!
    }

    fun setReports(reports: List<StudentDashboardReport>) {
        _reports.postValue(reports)
    }
}