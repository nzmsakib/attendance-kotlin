package com.nasakib.attendancems.ui.teacher.dashboard.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.nasakib.attendancems.R
import com.nasakib.attendancems.databinding.DialogCreateEditClassroomBinding

class CreateEditClassroomDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogCreateEditClassroomBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity(), R.style.Dialog)
        binding = DialogCreateEditClassroomBinding.inflate(layoutInflater)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.courses,
            R.layout.spinner_dropdown
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown)
            binding.spCourse.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sessions,
            R.layout.spinner_dropdown
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown)
            binding.spSession.adapter = adapter
        }

        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window!!.attributes.windowAnimations = R.style.DialogPopAnimation
        return builder.create()
    }

}