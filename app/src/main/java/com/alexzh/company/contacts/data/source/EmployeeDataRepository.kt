package com.alexzh.company.contacts.data.source

import com.alexzh.company.contacts.data.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class EmployeeDataRepository(private val localDataSource: EmployeeDataSource,
                             private val remoteDataSource: EmployeeDataSource): EmployeeRepository {

    override suspend fun saveEmployees(employees: List<Employee>) {
        return localDataSource.saveEmployees(employees)
    }

    override suspend fun fetchEmployeesByTeamId(teamId: Long): Flow<List<Employee>> {
        val localData = fetchLocalEmployees(teamId)
        return localData
                .flatMapLatest {
                    if (it.isEmpty()) {
                        fetchAndCacheRemoteEmployees(teamId)
                    } else {
                        localData
                    }
                }
    }

    private suspend fun fetchLocalEmployees(teamId: Long): Flow<List<Employee>> {
        return localDataSource.fetchEmployeesByTeamId(teamId)
    }

    private suspend fun fetchAndCacheRemoteEmployees(teamId: Long): Flow<List<Employee>> {
        return remoteDataSource.fetchEmployeesByTeamId(teamId)
                .onEach { saveEmployees(it) }
                .flowOn(Dispatchers.IO)
    }
}