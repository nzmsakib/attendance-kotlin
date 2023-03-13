package com.nasakib.attendancems.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nasakib.attendancems.databinding.ActivityDashboardBinding
import com.nasakib.attendancems.databinding.ActivityLoginBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}