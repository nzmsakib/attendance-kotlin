package com.nasakib.attendancems.ui.teacher.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nasakib.attendancems.databinding.ActivityDashboardTeacherBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardTeacherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewIntro.text = "Welcome to Teacher Dashboard"
    }
}