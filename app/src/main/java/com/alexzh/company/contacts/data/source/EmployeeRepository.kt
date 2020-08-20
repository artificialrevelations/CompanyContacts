package com.alexzh.company.contacts.data.source

import com.alexzh.company.contacts.data.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {

    suspend fun fetchEmployeesByTeamId(teamId: Long): Flow<List<Employee>>

    suspend fun saveEmployees(employees: List<Employee>)
}