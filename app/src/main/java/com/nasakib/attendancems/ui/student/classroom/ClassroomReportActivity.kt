package com.nasakib.attendancems.ui.student.classroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nasakib.attendancems.SessionManager
import com.nasakib.attendancems.apis.ApiClient
import com.nasakib.attendancems.apis.ApiService
import com.nasakib.attendancems.data.model.StudentClassroomReport
import com.nasakib.attendancems.databinding.ActivityClassroomReportStudentBinding
import com.nasakib.attendancems.ui.student.adapters.ClassroomReportAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ClassroomReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClassroomReportStudentBinding
    private lateinit var classroomReportViewModel: ClassroomReportViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var apiService: ApiService
    private var classroomId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassroomReportStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()
        apiService = apiClient.getApiService(this)

        classroomId = intent.getIntExtra("classroom", 0)
        val classname = intent.getStringExtra("course_name")

        classroomReportViewModel = ClassroomReportViewModel()
        binding.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        classroomReportViewModel.reports.observe(this@ClassroomReportActivity, Observer {
            val reports = it ?: return@Observer
            binding.recyclerView.adapter = ClassroomReportAdapter(this, reports)
        })

        binding.swipeRefresh.isRefreshing = true
        getReports(classroomId)

        binding.swipeRefresh.setOnRefreshListener {
            getReports(classroomId)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = classname
    }

    private fun getReports(classroomId: Int) {
        GlobalScope.launch {
            val result = apiService.getStudentClassroom(classroomId).execute()
            if (result.isSuccessful) {
                val response = result.body()
                if (response != null) {
                    if (response.status == "success") {
                        classroomReportViewModel.setReports(response.data)
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
}