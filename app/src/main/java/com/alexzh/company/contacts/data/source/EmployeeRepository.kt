package com.alexzh.company.contacts.data.source

import com.alexzh.company.contacts.data.Employee
import io.reactivex.Completable
import io.reactivex.Single

interface EmployeeRepository {

    fun fetchEmployeesByTeamId(teamId: Long): Single<List<Employee>>

    fun saveEmployees(employees: List<Employee>): Completable
}