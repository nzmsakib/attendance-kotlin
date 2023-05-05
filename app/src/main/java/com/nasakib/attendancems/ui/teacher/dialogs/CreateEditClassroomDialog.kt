package com.nasakib.attendancems.ui.teacher.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.nasakib.attendancems.R
import com.nasakib.attendancems.data.model.Course
import com.nasakib.attendancems.data.model.CreateClassroom
import com.nasakib.attendancems.data.model.Session
import com.nasakib.attendancems.databinding.DialogCreateEditClassroomBinding

class CreateEditClassroomDialog(listener: FormDialogSubmitListener<CreateClassroom>, courses: List<Course>?, sessions: List<Session>?) : AppCompatDialogFragment() {

    private lateinit var binding: DialogCreateEditClassroomBinding
    private val mListener = listener
    private val mCourses = courses
    private val mSessions = sessions

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity(), R.style.Dialog)
        binding = DialogCreateEditClassroomBinding.inflate(layoutInflater)

        binding.spCourse.adapter = ArrayAdapter(requireContext(), R.layout.spinner_dropdown, mCourses?.map { it.title } ?: listOf())

        binding.spSession.adapter = ArrayAdapter(requireContext(), R.layout.spinner_dropdown, mSessions?.map { it.title } ?: listOf())

        binding.btnCreate.setOnClickListener {
            val classroom = CreateClassroom(
                binding.spCourse.selectedItem.toString(),
                binding.spSession.selectedItem.toString(),
                binding.etClassroomName.text.toString()
            )
            mListener.onSubmit(classroom)
            dismiss()
        }

        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window!!.attributes.windowAnimations = R.style.DialogPopAnimation
        return builder.create()
    }
}