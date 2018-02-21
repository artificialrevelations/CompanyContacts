package com.alexzh.company.contacts.employees

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.data.Employee

class EmployeesAdapter(private val itemClick: (Employee) -> Unit): RecyclerView.Adapter<EmployeeViewHolder>() {
    private var employeeList: List<Employee> = listOf()

    fun setEmployeeList(employeeList: List<Employee>) {
        this.employeeList = employeeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        return EmployeeViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bindEmployee(employeeList[position])
    }

    override fun getItemCount(): Int = employeeList.size
}