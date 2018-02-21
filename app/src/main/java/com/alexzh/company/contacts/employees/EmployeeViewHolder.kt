package com.alexzh.company.contacts.employees

import android.support.v7.widget.RecyclerView
import android.view.View
import com.alexzh.company.contacts.data.Employee
import kotlinx.android.synthetic.main.item_employee.view.*

class EmployeeViewHolder(view: View, private val itemClick: (Employee) -> Unit): RecyclerView.ViewHolder(view) {

    fun bindEmployee(employee: Employee) {
        with(employee) {
            itemView.employeeName.text = name
            itemView.employeePhone.text = phone
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}