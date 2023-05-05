package com.nasakib.attendancems.ui.teacher.attendance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.nasakib.attendancems.R
import com.nasakib.attendancems.SessionManager
import com.nasakib.attendancems.apis.ApiClient
import com.nasakib.attendancems.apis.ApiService
import com.nasakib.attendancems.data.model.Attendance
import com.nasakib.attendancems.data.model.CreateAttendance
import com.nasakib.attendancems.data.model.EnrollAttendance
import com.nasakib.attendancems.data.model.StudentAttendance
import com.nasakib.attendancems.databinding.ActivityAttendanceBinding
import com.nasakib.attendancems.databinding.ActivityClassroomBinding
import com.nasakib.attendancems.ui.teacher.adapters.AttendanceAdapter
import com.nasakib.attendancems.ui.teacher.adapters.ClassroomAdapter
import com.nasakib.attendancems.ui.teacher.classroom.ClassroomViewModel
import com.nasakib.attendancems.ui.teacher.dialogs.CreateEditAttendanceDialog
import com.nasakib.attendancems.ui.teacher.dialogs.FormDialogSubmitListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AttendanceActivity : AppCompatActivity(), FormDialogSubmitListener<EnrollAttendance> {
    private lateinit var binding: ActivityAttendanceBinding
    private lateinit var attendanceViewModel: AttendanceViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var apiService: ApiService
    private var attendanceId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()
        apiService = apiClient.getApiService(this)

        attendanceId = intent.getIntExtra("attendance", 0)
        val attendanceName = intent.getStringExtra("attendance_name")

        attendanceViewModel = AttendanceViewModel()
        binding.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        attendanceViewModel.attendances.observe(this@AttendanceActivity, Observer {
            val studentAttendances = it ?: return@Observer

            binding.recyclerView.adapter = AttendanceAdapter(this, studentAttendances)
        })

        binding.swipeRefresh.isRefreshing = true
        refresh(attendanceId)

        binding.buttonEnrollAttendance.setOnClickListener() {
            val studentAttendances = attendanceViewModel.attendances.value ?: return@setOnClickListener

            Log.d(this.javaClass.name, "StudentAttendance: $studentAttendances")

            binding.swipeRefresh.isRefreshing = true
            enrollAttendance(
                EnrollAttendance(
                    students = studentAttendances.filter { it.present }.map { it.student.id },
                )
            )
        }

        binding.switchPresentAbsentAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                attendanceViewModel.setAllPresent()
            } else {
                attendanceViewModel.setAllAbsent()
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            refresh(attendanceId)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = attendanceName
    }

    private fun refresh(attendanceId: Int) {
        GlobalScope.launch {
            val result = apiService.getTeacherAttendance(attendanceId).execute()
            if (result.isSuccessful) {
                val response = result.body()
                if (response != null) {
                    if (response.status == "success") {
                        val students = response.data[0].students
                        val studentsActive = response.data[0].students_active
                        attendanceViewModel.setStudentAttendances(students.map { student ->
                            StudentAttendance(
                                student,
                                studentsActive.contains(student.id)
                            ) })
                    } else {
                        Log.d(this.javaClass.name, "Dash: ${response.message}")
                    }
                } else {
                    Log.d(this.javaClass.name, "Dash: ${result.errorBody()}")
                }
            } else {
                Log.d(this.javaClass.name, "Dash: ${result.errorBody()}")
            }
            runOnUiThread {
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun enrollAttendance(attendance: EnrollAttendance) {
        when {
            attendance.students.isEmpty() -> {
                Log.d(this.javaClass.name, "createAttendance: students is empty")
                return
            }
            else -> {
                Log.d(this.javaClass.name, "createAttendance: ${attendance.students}")

                GlobalScope.launch {
                    val result = apiService.enrollAttendance(attendanceId, attendance).execute()
                    if (result.isSuccessful) {
                        val response = result.body()
                        if (response != null) {
                            if (response.status == "success") {
                                runOnUiThread() {
                                    binding.swipeRefresh.isRefreshing = true
                                    refresh(attendanceId)
                                }
                            } else {
                                Log.d(this.javaClass.name, "Dash: ${response.message}")
                            }
                        } else {
                            Log.d(this.javaClass.name, "Dash: ${result.errorBody()}")
                        }
                    } else {
                        Log.d(this.javaClass.name, "Dash: ${result.errorBody()}")
                    }
                }
            }
        }
    }
}