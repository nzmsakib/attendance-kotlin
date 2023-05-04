package com.nasakib.attendancems.ui.teacher.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nasakib.attendancems.R
import com.nasakib.attendancems.SessionManager
import com.nasakib.attendancems.apis.ApiClient
import com.nasakib.attendancems.databinding.ActivityDashboardTeacherBinding
import com.nasakib.attendancems.ui.login.LoginActivity

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardTeacherBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var dashboardViewModel: DashboardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        apiClient = ApiClient()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Teacher Dashboard"

        dashboardViewModel = DashboardViewModel()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.swipeRefresh.setOnRefreshListener {
            dashboardViewModel.refresh()
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