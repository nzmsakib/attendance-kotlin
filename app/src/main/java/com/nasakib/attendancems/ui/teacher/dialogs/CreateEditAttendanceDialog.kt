package com.nasakib.attendancems.ui.teacher.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.nasakib.attendancems.R
import com.nasakib.attendancems.data.model.Course
import com.nasakib.attendancems.data.model.CreateAttendance
import com.nasakib.attendancems.data.model.CreateClassroom
import com.nasakib.attendancems.data.model.Session
import com.nasakib.attendancems.databinding.DialogCreateEditAttendanceSheetBinding
import com.nasakib.attendancems.databinding.DialogCreateEditClassroomBinding

class CreateEditAttendanceDialog(listener: FormDialogSubmitListener<CreateAttendance>) : AppCompatDialogFragment() {

    private lateinit var binding: DialogCreateEditAttendanceSheetBinding
    private val mListener = listener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity(), R.style.Dialog)
        binding = DialogCreateEditAttendanceSheetBinding.inflate(layoutInflater)

        binding.btnCreate.setOnClickListener {
            val attendance = CreateAttendance(
                binding.etAttendanceName.text.toString(),
                binding.etAttendanceScore.text.toString().toFloat()
            )
            mListener.onSubmit(attendance)
            dismiss()
        }

        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window!!.attributes.windowAnimations = R.style.DialogPopAnimation
        return builder.create()
    }
}