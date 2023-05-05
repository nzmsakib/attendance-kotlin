package com.nasakib.attendancems.ui.student.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nasakib.attendancems.R
import com.nasakib.attendancems.SessionManager
import com.nasakib.attendancems.apis.ApiClient
import com.nasakib.attendancems.apis.ApiService
import com.nasakib.attendancems.data.model.StudentDashboardReport
import com.nasakib.attendancems.databinding.ActivityDashboardStudentBinding
import com.nasakib.attendancems.ui.login.LoginActivity
import com.nasakib.attendancems.ui.student.adapters.DashboardReportAdapter
import com.nasakib.attendancems.ui.student.classroom.ClassroomReportActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardStudentBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var toolbar: Toolbar
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var listView: ListView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        listView = binding.list
        swipeRefreshLayout = binding.swipeRefresh

        listView.setOnItemClickListener { parent, view, position, id ->
            val report = parent.getItemAtPosition(position) as StudentDashboardReport

            Intent (this, ClassroomReportActivity::class.java).also {
                it.putExtra("classroom", report.classroom.id)
                it.putExtra("course_name", report.course.code)
                startActivity(it)
            }
        }

        dashboardViewModel = DashboardViewModel()

        dashboardViewModel.reports.observe(this@DashboardActivity, Observer {
            val reports = it ?: return@Observer
            Log.d("DashActivity", "Reports: $reports")
            val adapter = DashboardReportAdapter(this, R.layout.report_row, reports)
            listView.adapter = adapter
            swipeRefreshLayout.isRefreshing = false
        })

        apiService = apiClient.getApiService(this)

        swipeRefreshLayout.isRefreshing = true
        this.refresh()

        swipeRefreshLayout.setOnRefreshListener {
            this.refresh()
        }
        supportActionBar?.title = "Dashboard"
    }

    private fun refresh() {
        GlobalScope.launch {
            val result = apiService.getStudentHome().execute()
            if (result.isSuccessful) {
                val response = result.body()
                if (response != null) {
                    if (response.status == "success") {
                        dashboardViewModel.setReports(response.data)
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
}