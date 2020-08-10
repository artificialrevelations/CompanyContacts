package com.alexzh.company.contacts.employees

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.company.contacts.data.Employee
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_employee.view.*

class EmployeeViewHolder(view: View, private val itemClick: (Employee) -> Unit): RecyclerView.ViewHolder(view) {

    fun bindEmployee(employee: Employee) {
        with(employee) {
            itemView.employeeName.text = name
            itemView.employeePhone.text = phone
            itemView.employeePosition.text = position
            itemView.setOnClickListener { itemClick(this) }

            Glide.with(itemView.context)
                    .load(photo)
                    .apply(RequestOptions.circleCropTransform())
                    .into(itemView.employeePhoto)
        }
    }
}