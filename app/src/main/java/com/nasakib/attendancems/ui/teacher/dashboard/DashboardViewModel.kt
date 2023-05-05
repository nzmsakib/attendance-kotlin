package com.nasakib.attendancems.ui.teacher.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nasakib.attendancems.data.model.TeacherDashboardResponse

class DashboardViewModel {
    private val _dashboardData = MutableLiveData<TeacherDashboardResponse>()
    val dashboardData: LiveData<TeacherDashboardResponse> = _dashboardData

    fun setDashboardData(dashboardData: TeacherDashboardResponse) {
        _dashboardData.postValue(dashboardData)
        Log.d(this.javaClass.name, "setDashboardData: $dashboardData")
    }
}