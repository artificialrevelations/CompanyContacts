package com.alexzh.company.contacts.data.source.local

import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.source.EmployeeDataSource
import kotlinx.coroutines.flow.Flow

class LocalEmployeeDataSource(private val database: ContactsDatabase): EmployeeDataSource{

    override suspend fun fetchEmployeesByTeamId(teamId: Long): Flow<List<Employee>> {
        return database.employeesDao().getEmployeesByTeamId(teamId)
    }

    override suspend fun saveEmployees(employees: List<Employee>) {
        database.employeesDao().insertEmployees(employees)
    }
}