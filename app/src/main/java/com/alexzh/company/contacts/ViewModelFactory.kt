package com.alexzh.company.contacts

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.alexzh.company.contacts.employees.EmployeesViewModel
import com.alexzh.company.contacts.teams.TeamsViewModel

class ViewModelFactory(private val viewModel: ViewModel) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamsViewModel::class.java)) {
            return viewModel as T
        } else if (modelClass.isAssignableFrom(EmployeesViewModel::class.java)) {
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
