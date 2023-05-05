package com.nasakib.attendancems.ui.teacher.dialogs

interface FormDialogSubmitListener<T> {
    fun onSubmit(data: T) {}
}