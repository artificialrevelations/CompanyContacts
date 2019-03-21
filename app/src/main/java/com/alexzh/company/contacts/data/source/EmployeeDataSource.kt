package com.alexzh.company.contacts.data.source

import android.arch.lifecycle.LiveData
import com.alexzh.company.contacts.data.Employee
import io.reactivex.Completable
import io.reactivex.Single

interface EmployeeDataSource {

    fun fetchEmployeesByTeamIdRx(teamId: Long): Single<List<Employee>>

    fun fetchEmployeesByTeamIdLD(teamId: Long): LiveData<List<Employee>>

    fun saveEmployees(employees: List<Employee>): Completable
}