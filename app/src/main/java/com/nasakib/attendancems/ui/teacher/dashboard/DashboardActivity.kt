package com.nasakib.attendancems.ui.teacher.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nasakib.attendancems.R
import com.nasakib.attendancems.SessionManager
import com.nasakib.attendancems.apis.ApiClient
import com.nasakib.attendancems.apis.ApiService
import com.nasakib.attendancems.data.model.Classroom
import com.nasakib.attendancems.data.model.CreateClassroom
import com.nasakib.attendancems.databinding.ActivityDashboardTeacherBinding
import com.nasakib.attendancems.ui.login.LoginActivity
import com.nasakib.attendancems.ui.teacher.adapters.DashboardAdapter
import com.nasakib.attendancems.ui.teacher.dialogs.CreateEditClassroomDialog
import com.nasakib.attendancems.ui.teacher.dialogs.FormDialogSubmitListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity(), FormDialogSubmitListener<CreateClassroom> {
    private lateinit var binding: ActivityDashboardTeacherBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var apiService: ApiService
    private lateinit var dashboardViewModel: DashboardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()
        apiService = apiClient.getApiService(this)

        supportActionBar?.title = "Teacher Dashboard"

        dashboardViewModel = DashboardViewModel()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.swipeRefresh.isRefreshing = true
        refresh()

        binding.buttonCreateClassroom.setOnClickListener() {
            CreateEditClassroomDialog(this, dashboardViewModel.dashboardData.value?.courses, dashboardViewModel.dashboardData.value?.sessions).show(supportFragmentManager, "CreateEditClassroomDialog")
        }

        dashboardViewModel.dashboardData.observe(this@DashboardActivity) {
            val dashboardData = it ?: return@observe
            binding.recyclerView.adapter = DashboardAdapter(this, dashboardData.classrooms)
        }

        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
    }

    private fun refresh() {
        GlobalScope.launch {
            val result = apiService.getTeacherHome().execute()
            if (result.isSuccessful) {
                val response = result.body()
                if (response != null) {
                    if (response.status == "success") {
                        dashboardViewModel.setDashboardData(response.data[0])
                    } else {
                        Log.d(this.javaClass.name, "Dash: ${response.message}")
                    }
                } else {
                    Log.d(this.javaClass.name, "Dash: ${result.errorBody()}")
                }
            } else {
                Log.d(this.javaClass.name, "Dash: ${result.errorBody()}")
            }
            runOnUiThread() {
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_logout -> {
                sessionManager.clearAuthToken()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSubmit(data: CreateClassroom) {
        Log.d(this.javaClass.name, "onSubmit: $data")
        createClassroom(data)
        super.onSubmit(data)
    }

    private fun createClassroom(classroom: CreateClassroom) {
        val course = dashboardViewModel.dashboardData.value?.courses?.find { it.title == classroom.course }
        val session = dashboardViewModel.dashboardData.value?.sessions?.find { it.title == classroom.session }

        when {
            course == null -> {
                Log.d(this.javaClass.name, "createClassroom: Course not found")
                return
            }
            session == null -> {
                Log.d(this.javaClass.name, "createClassroom: Session not found")
                return
            }
            classroom.name.isEmpty() -> {
                Log.d(this.javaClass.name, "createClassroom: Classroom name is empty")
                return
            }
            else -> {
                Log.d(this.javaClass.name, "createClassroom: Creating classroom")

                val room = Classroom(
                    name = classroom.name,
                    course_id = course.id,
                    session_id = session.id
                )
                GlobalScope.launch {
                    val result = apiService.createClassroom(room).execute()
                    if (result.isSuccessful) {
                        val response = result.body()
                        if (response != null) {
                            if (response.status == "success") {
                                runOnUiThread() {
                                    binding.swipeRefresh.isRefreshing = true
                                    refresh()
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