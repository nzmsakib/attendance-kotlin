package com.nasakib.attendancems.ui.teacher.classroom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nasakib.attendancems.data.model.Attendance

class ClassroomViewModel {
    private val _attendances = MutableLiveData<List<Attendance>>()
    val attendances: LiveData<List<Attendance>> = _attendances

    fun setClassroomData(attendances: List<Attendance>) {
        _attendances.postValue(attendances)
        Log.d(this.javaClass.name, "setClassroomData: $attendances")
    }
}