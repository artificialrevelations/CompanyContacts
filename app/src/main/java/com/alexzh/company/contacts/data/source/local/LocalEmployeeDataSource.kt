package com.alexzh.company.contacts.data.source.local

import android.arch.lifecycle.LiveData
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.source.EmployeeDataSource
import io.reactivex.Completable
import io.reactivex.Single

class LocalEmployeeDataSource(private val database: ContactsDatabase) : EmployeeDataSource {

    override fun fetchEmployeesByTeamIdLD(teamId: Long): LiveData<List<Employee>> {
        return database.employeesDao().getEmployeesByTeamIdLD(teamId)
    }

    override fun fetchEmployeesByTeamIdRx(teamId: Long): Single<List<Employee>> {
        return database.employeesDao().getEmployeesByTeamIdRx(teamId)
    }

    override fun saveEmployees(employees: List<Employee>): Completable {
        database.employeesDao().insertEmployees(employees)
        return Completable.complete()
    }

}