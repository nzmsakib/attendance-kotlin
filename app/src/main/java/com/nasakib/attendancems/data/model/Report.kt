package com.nasakib.attendancems.data.model

data class Report(
    val label: String,
    val value: Float,
    val children: List<ChildReport> = listOf(),
)
