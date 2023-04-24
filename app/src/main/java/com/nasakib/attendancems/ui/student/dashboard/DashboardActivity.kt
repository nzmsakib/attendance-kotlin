package com.nasakib.attendancems.ui.student.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nasakib.attendancems.R
import com.nasakib.attendancems.SessionManager
import com.nasakib.attendancems.apis.ApiClient
import com.nasakib.attendancems.databinding.ActivityDashboardStudentBinding
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        toolbar = binding.toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Student Dashboard"

        listView = binding.list
        swipeRefreshLayout = binding.swipeRefresh

        listView.setOnItemClickListener { parent, view, position, id ->
            val report = dashboardViewModel.getReports()[position]
            Toast.makeText(this, "${report.label}: ${report.value}", Toast.LENGTH_SHORT).show()
        }

        dashboardViewModel = DashboardViewModel()

        dashboardViewModel.reports.observe(this@DashboardActivity, Observer {
            val reports = it ?: return@Observer
            Log.d("DashActivity", "Reports: $reports")
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reports.map { report -> report.label })
            listView.adapter = adapter
        })

        val apiService = apiClient.getApiService(this)

        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            // empty list
            dashboardViewModel.setReports(emptyList())
            GlobalScope.launch {
                val result = apiService.getStudentHome(2161).execute()
                if (result.isSuccessful) {
                    val response = result.body()
                    if (response != null) {
                        if (response.status == "success") {
                            dashboardViewModel.setReports(response.data)
                        } else {
                            Log.d("DashActivity", "Dash: ${response.message}")
                        }
                    } else {
                        Log.d("DashActivity", "Dash: ${result.errorBody()}")
                    }
                } else {
                    Log.d("DashActivity", "Dash: ${result.errorBody()}")
                }
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_logout -> {
                sessionManager.clearAuthToken()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}