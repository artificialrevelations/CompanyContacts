package com.alexzh.company.contacts.employees

import androidx.lifecycle.ViewModel
import com.alexzh.company.contacts.SchedulersProvider
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.source.EmployeeRepository
import io.reactivex.Single

class EmployeesViewModel(private val employeeRepository: EmployeeRepository,
                         private val schedulersProvider: SchedulersProvider): ViewModel() {

    fun fetchEmployeesById(teamId: Long): Single<List<Employee>> {
        return employeeRepository.fetchEmployeesByTeamId(teamId)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
    }
}