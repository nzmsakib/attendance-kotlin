package com.nasakib.attendancems.ui.teacher.classroom

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nasakib.attendancems.SessionManager
import com.nasakib.attendancems.apis.ApiClient
import com.nasakib.attendancems.apis.ApiService
import com.nasakib.attendancems.data.model.Attendance
import com.nasakib.attendancems.data.model.Classroom
import com.nasakib.attendancems.data.model.CreateAttendance
import com.nasakib.attendancems.data.model.CreateClassroom
import com.nasakib.attendancems.databinding.ActivityClassroomBinding
import com.nasakib.attendancems.ui.student.adapters.ClassroomReportAdapter
import com.nasakib.attendancems.ui.teacher.adapters.ClassroomAdapter
import com.nasakib.attendancems.ui.teacher.dialogs.CreateEditAttendanceDialog
import com.nasakib.attendancems.ui.teacher.dialogs.CreateEditClassroomDialog
import com.nasakib.attendancems.ui.teacher.dialogs.FormDialogSubmitListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ClassroomActivity : AppCompatActivity(), FormDialogSubmitListener<CreateAttendance> {
    private lateinit var binding: ActivityClassroomBinding
    private lateinit var classroomViewModel : ClassroomViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var apiService: ApiService
    private var classroomId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassroomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()
        apiService = apiClient.getApiService(this)

        classroomId = intent.getIntExtra("classroom", 0)
        val classname = intent.getStringExtra("course_name")

        classroomViewModel = ClassroomViewModel()
        binding.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        classroomViewModel.attendances.observe(this@ClassroomActivity, Observer {
            val attendances = it ?: return@Observer
            binding.recyclerView.adapter = ClassroomAdapter(this, attendances)
        })

        binding.swipeRefresh.isRefreshing = true
        refresh(classroomId)

        binding.buttonCreateAttendance.setOnClickListener() {
            CreateEditAttendanceDialog(this).show(supportFragmentManager, "CreateEditClassroomDialog")
        }

        binding.swipeRefresh.setOnRefreshListener {
            refresh(classroomId)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = classname
    }

    private fun refresh(classroomId: Int) {
        GlobalScope.launch {
            val result = apiService.getTeacherClassroom(classroomId).execute()
            if (result.isSuccessful) {
                val response = result.body()
                if (response != null) {
                    if (response.status == "success") {
                        classroomViewModel.setClassroomData(response.data[0].attendances)
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

    override fun onSubmit(data: CreateAttendance) {
        createAttendance(data)
        super.onSubmit(data)
    }

    private fun createAttendance(attendance: CreateAttendance) {
        when {
            attendance.name.isEmpty() -> {
                Log.d(this.javaClass.name, "createAttendance: Name is empty")
                return
            }
            else -> {
                Log.d(this.javaClass.name, "createAttendance: ${attendance.name}")

                val attend = Attendance(
                    classroom_id = classroomId,
                    name = attendance.name,
                    score = attendance.score,
                )
                GlobalScope.launch {
                    val result = apiService.createAttendance(classroomId, attend).execute()
                    if (result.isSuccessful) {
                        val response = result.body()
                        if (response != null) {
                            if (response.status == "success") {
                                runOnUiThread() {
                                    binding.swipeRefresh.isRefreshing = true
                                    refresh(classroomId)
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