package com.alexzh.company.contacts.data.source.remote

import android.arch.lifecycle.LiveData
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.source.EmployeeDataSource
import io.reactivex.Completable
import io.reactivex.Single

class RemoteEmployeeDataSource(private val apiService: ContactsApiService) : EmployeeDataSource {
    override fun fetchEmployeesByTeamIdRx(teamId: Long): Single<List<Employee>> {
        return apiService.fetchEmployeesByTeamIdRx(teamId)
    }

    override fun fetchEmployeesByTeamIdLD(teamId: Long): LiveData<List<Employee>> {
        return apiService.fetchEmployeesByTeamIdLD(teamId)
    }

    override fun saveEmployees(employees: List<Employee>): Completable {
        throw UnsupportedOperationException()
    }
}