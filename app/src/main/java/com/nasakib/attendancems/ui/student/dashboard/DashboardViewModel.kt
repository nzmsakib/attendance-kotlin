package com.nasakib.attendancems.ui.student.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nasakib.attendancems.data.model.Report

class DashboardViewModel {
    private val _reports = MutableLiveData<List<Report>>()
    val reports: LiveData<List<Report>> = _reports

    fun getReports() : List<Report> {
        return _reports.value!!
    }

    fun setReports(reports: List<Report>) {
        _reports.postValue(reports)
    }
}