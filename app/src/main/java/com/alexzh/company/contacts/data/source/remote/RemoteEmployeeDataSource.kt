package com.alexzh.company.contacts.data.source.remote

import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.source.EmployeeDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteEmployeeDataSource(private val apiService: ContactsApiService): EmployeeDataSource {

    override suspend fun fetchEmployeesByTeamId(teamId: Long): Flow<List<Employee>> {
        return flow {
            emit(apiService.fetchEmployeesByTeamId(teamId))
        }
    }

    override suspend fun saveEmployees(employees: List<Employee>) {
        throw UnsupportedOperationException()
    }
}