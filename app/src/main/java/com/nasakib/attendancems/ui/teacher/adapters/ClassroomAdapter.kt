package com.nasakib.attendancems.ui.teacher.adapters

import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nasakib.attendancems.R
import com.nasakib.attendancems.data.model.Attendance
import com.nasakib.attendancems.databinding.TeacherClassroomAttendanceSheetListBinding
import com.nasakib.attendancems.ui.teacher.attendance.AttendanceActivity
import com.nasakib.attendancems.ui.teacher.classroom.ClassroomActivity
import java.text.SimpleDateFormat
import java.util.Locale

class ClassroomAdapter(context: Context, objects: List<Attendance>) : RecyclerView.Adapter<ClassroomAdapter.ViewHolder>() {
    private val mContext: Context = context
    private val mObjects: List<Attendance> = objects

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: TeacherClassroomAttendanceSheetListBinding = TeacherClassroomAttendanceSheetListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.teacher_classroom_attendance_sheet_list,
                parent,
                false,
            )
        )
    }

    override fun getItemCount(): Int {
        return mObjects.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val attendance = mObjects[position]

        holder.binding.tvAttendance.text = attendance.name
        holder.binding.tvAttendanceTime.text = attendance.created_at.substring(0, 10) + " " + attendance.created_at.substring(11, 19)

        holder.binding.root.setOnClickListener {
            val intent = Intent(mContext, AttendanceActivity::class.java).also {
                it.putExtra("attendance", attendance.id)
                it.putExtra("attendance_name", attendance.name)
            }
            mContext.startActivity(intent)
        }
    }
}