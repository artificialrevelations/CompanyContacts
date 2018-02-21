package com.alexzh.company.contacts.data.source

import com.alexzh.company.contacts.data.Employee
import io.reactivex.Completable
import io.reactivex.Single

class EmployeeDataRepository(private val localDataSource: EmployeeDataSource,
                             private val remoteDataSource: EmployeeDataSource): EmployeeRepository {

    override fun saveEmployees(employees: List<Employee>): Completable {
        return localDataSource.saveEmployees(employees)
    }

    override fun fetchEmployeesByTeamId(teamId: Long): Single<List<Employee>> {
        return Single.concat(fetchLocalEmployees(teamId), fetchAndCacheRemoteEmployees(teamId))
                .filter { employees -> employees.isNotEmpty() }
                .first(listOf())
    }

    private fun fetchLocalEmployees(teamId: Long): Single<List<Employee>> {
        return localDataSource.fetchEmployeesByTeamId(teamId)
    }

    private fun fetchAndCacheRemoteEmployees(teamId: Long): Single<List<Employee>> {
        return remoteDataSource.fetchEmployeesByTeamId(teamId)
                .doOnSuccess { saveEmployees(it) }
    }
}