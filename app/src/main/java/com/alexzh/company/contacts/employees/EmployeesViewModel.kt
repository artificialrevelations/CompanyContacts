package com.alexzh.company.contacts.employees

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.alexzh.company.contacts.SchedulersProvider
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.source.EmployeeRepository
import io.reactivex.Single

class EmployeesViewModel(private val employeeRepository: EmployeeRepository,
                         private val schedulersProvider: SchedulersProvider): ViewModel() {

    fun fetchEmployeesByIdRx(teamId: Long): Single<List<Employee>> {
        return employeeRepository.fetchEmployeesByTeamIdRx(teamId)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
    }

    fun fetchEmployeesByIdLD(teamId: Long): LiveData<List<Employee>> {
        return employeeRepository.fetchEmployeesByTeamIdLD(teamId)
    }
}