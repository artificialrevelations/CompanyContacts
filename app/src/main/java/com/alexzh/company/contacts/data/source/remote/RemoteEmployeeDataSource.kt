package com.alexzh.company.contacts.data.source.remote

import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.source.EmployeeDataSource
import io.reactivex.Completable
import io.reactivex.Single

class RemoteEmployeeDataSource(private val apiService: ContactsApiService): EmployeeDataSource {

    override fun fetchEmployeesByTeamId(teamId: Long): Single<List<Employee>> {
        return apiService.fetchEmployeesByTeamId(teamId)
    }

    override fun saveEmployees(employees: List<Employee>): Completable {
        throw UnsupportedOperationException()
    }
}