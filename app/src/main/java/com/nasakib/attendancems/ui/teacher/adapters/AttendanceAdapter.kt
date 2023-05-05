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
import com.nasakib.attendancems.data.model.Student
import com.nasakib.attendancems.data.model.StudentAttendance
import com.nasakib.attendancems.databinding.TeacherClassroomAttendanceSheetBinding
import com.nasakib.attendancems.databinding.TeacherClassroomAttendanceSheetListBinding
import com.nasakib.attendancems.ui.teacher.attendance.AttendanceActivity
import com.nasakib.attendancems.ui.teacher.classroom.ClassroomActivity
import java.text.SimpleDateFormat
import java.util.Locale

class AttendanceAdapter(context: Context, objects: List<StudentAttendance>) : RecyclerView.Adapter<AttendanceAdapter.ViewHolder>() {
    private val mContext: Context = context
    private val mObjects: List<StudentAttendance> = objects

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: TeacherClassroomAttendanceSheetBinding = TeacherClassroomAttendanceSheetBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.teacher_classroom_attendance_sheet,
                parent,
                false,
            )
        )
    }

    override fun getItemCount(): Int {
        return mObjects.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = mObjects[position]

        holder.binding.roll.text = item.student.roll.toString()
        holder.binding.active.isChecked = item.present
        holder.binding.active.setOnCheckedChangeListener { _, isChecked ->
            item.present = isChecked
        }


        holder.binding.root.setOnClickListener {
            item.present = !item.present
            notifyItemChanged(position)
        }
    }
}