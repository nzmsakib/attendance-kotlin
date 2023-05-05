package com.nasakib.attendancems.ui.teacher.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nasakib.attendancems.R
import com.nasakib.attendancems.data.model.Classroom
import com.nasakib.attendancems.databinding.TeacherHomeClassroomListBinding
import com.nasakib.attendancems.ui.teacher.classroom.ClassroomActivity

class DashboardAdapter(context: Context, objects: List<Classroom>) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    private val mContext: Context = context
    private val mObjects: List<Classroom> = objects

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: TeacherHomeClassroomListBinding = TeacherHomeClassroomListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.teacher_home_classroom_list,
                parent,
                false,
            )
        )
    }

    override fun getItemCount(): Int {
        return mObjects.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val classroom = mObjects[position]
        holder.binding.tvClassroom.text = classroom.name + " (" + classroom.course?.department?.abbr + "'" + classroom.session?.abbr + ")"
        holder.binding.tvCourse.text = classroom.course?.code

        holder.binding.root.setOnClickListener {
            val intent = Intent(mContext, ClassroomActivity::class.java).also {
                it.putExtra("classroom", classroom.id)
                it.putExtra("course_name", classroom.course?.code + " (" + classroom.name + ", " + classroom.course?.department?.abbr + "'" + classroom.session?.abbr + ")")
            }
            mContext.startActivity(intent)
        }
    }
}