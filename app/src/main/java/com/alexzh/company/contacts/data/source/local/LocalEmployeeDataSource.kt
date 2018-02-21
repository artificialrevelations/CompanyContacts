package com.alexzh.company.contacts.data.source.local

import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.source.EmployeeDataSource
import io.reactivex.Completable
import io.reactivex.Single

class LocalEmployeeDataSource(private val database: ContactsDatabase): EmployeeDataSource{

    override fun fetchEmployeesByTeamId(teamId: Long): Single<List<Employee>> {
        return database.employeesDao().getEmployeesByTeamId(teamId)
    }

    override fun saveEmployees(employees: List<Employee>): Completable {
        database.employeesDao().insertEmployees(employees)
        return Completable.complete()
    }

}