package com.nasakib.attendancems.ui.student.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nasakib.attendancems.data.model.StudentClassroomReport
import com.nasakib.attendancems.databinding.ReportRowBinding

class ClassroomReportAdapter(context: Context, objects: List<StudentClassroomReport>) : RecyclerView.Adapter<ClassroomReportAdapter.ViewHolder>() {
    private val mContext: Context = context
    private val mObjects: List<StudentClassroomReport> = objects

    init {
        Log.d(this.javaClass.name, "ClassroomReportAdapter: $mObjects")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(this.javaClass.name, "onCreateViewHolder: $mObjects")
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                com.nasakib.attendancems.R.layout.report_row,
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val report = mObjects[position]
        holder.reportRowBinding.reportLabel.text = report.attendance.name
        holder.reportRowBinding.reportValue.text = report.score.toString() + "/" + report.attendance.score.toString()
        Log.d(this.javaClass.name, "Report: $report")
    }

    override fun getItemCount(): Int {
        return mObjects.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var reportRowBinding: ReportRowBinding = ReportRowBinding.bind(itemView)
    }
}