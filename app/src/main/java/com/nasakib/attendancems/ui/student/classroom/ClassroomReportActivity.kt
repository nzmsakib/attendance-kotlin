package com.nasakib.attendancems.ui.student.classroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
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
    private lateinit var toolbar: Toolbar
    var classroomId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClassroomReportStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()
        apiService = apiClient.getApiService(this)

        toolbar = binding.toolbar

        setSupportActionBar(toolbar)

        supportActionBar?.title = "Classroom Report"

        classroomId = intent.getIntExtra("classroom", 0)

        classroomReportViewModel = ClassroomReportViewModel()
        binding.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        getReports(classroomId)

        classroomReportViewModel.reports.observe(this@ClassroomReportActivity) {
            Log.d(this.javaClass.name, "Reports: $it")
            binding.recyclerView.adapter = ClassroomReportAdapter(this, it)
        }
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
        }
    }
}