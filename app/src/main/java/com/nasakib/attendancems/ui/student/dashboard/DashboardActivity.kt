package com.nasakib.attendancems.ui.student.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nasakib.attendancems.databinding.ActivityDashboardStudentBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewIntro.text = "Welcome to Student Dashboard"
    }
}