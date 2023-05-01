package com.nasakib.attendancems.ui.student.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.nasakib.attendancems.data.model.StudentDashboardReport
import com.nasakib.attendancems.databinding.ReportRowBinding


class DashboardReportAdapter(context: Context, resource: Int, objects: List<StudentDashboardReport>) :
    ArrayAdapter<StudentDashboardReport>(context, resource, objects) {
    private val mContext: Context = context
    private val mResource: Int = resource
    private lateinit var reportRowBinding: ReportRowBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = convertView

        reportRowBinding = ReportRowBinding.inflate(LayoutInflater.from(parent.context))

        if (v == null) {
            val vi: LayoutInflater = LayoutInflater.from(mContext)
            v = vi.inflate(mResource, null, true)
        }

        val p: StudentDashboardReport? = getItem(position)
        val tv1 = v?.findViewById(reportRowBinding.reportLabel.id) as TextView
        val tv2 = v.findViewById(reportRowBinding.reportValue.id) as TextView

        if (p != null) {
            tv1.text = p.course.code
            tv2.text = (p.attendancePercentage.toString() + "%")
        }

        return v!!
    }
}