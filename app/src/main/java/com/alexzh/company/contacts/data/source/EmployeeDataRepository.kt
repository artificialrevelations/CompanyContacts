package com.alexzh.company.contacts.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.livedata.Transformations2
import io.reactivex.Completable
import io.reactivex.Single

class EmployeeDataRepository(private val localDataSource: EmployeeDataSource,
                             private val remoteDataSource: EmployeeDataSource): EmployeeRepository {
    override fun fetchEmployeesByTeamIdRx(teamId: Long): Single<List<Employee>> {
        return Single.concat(fetchLocalEmployeesRx(teamId), fetchAndCacheRemoteEmployeesRx(teamId))
                .filter { employees -> employees.isNotEmpty() }
                .first(listOf())
    }

    override fun fetchEmployeesByTeamIdLD(teamId: Long): LiveData<List<Employee>> {
        val concatLiveData = Transformations2.concat(
                fetchLocalEmployeesLD(teamId), fetchAndCacheRemoteEmployeesLD(teamId)
        )

        //TODO: Single LiveData (LiveData that sends once and never more?)
        return Transformations2.filter(concatLiveData) {
            it.isNotEmpty()
        }
    }

    override fun saveEmployees(employees: List<Employee>): Completable {
        return localDataSource.saveEmployees(employees)
    }


    private fun fetchLocalEmployeesRx(teamId: Long): Single<List<Employee>> {
        return localDataSource.fetchEmployeesByTeamIdRx(teamId)
    }

    private fun fetchLocalEmployeesLD(teamId: Long): LiveData<List<Employee>> {
        return localDataSource.fetchEmployeesByTeamIdLD(teamId)
    }

    private fun fetchAndCacheRemoteEmployeesRx(teamId: Long): Single<List<Employee>> {
        return remoteDataSource.fetchEmployeesByTeamIdRx(teamId)
                .doOnSuccess { saveEmployees(it) }
    }

    private fun fetchAndCacheRemoteEmployeesLD(teamId: Long): LiveData<List<Employee>> {
        val cachingLiveData = MediatorLiveData<List<Employee>>()
        cachingLiveData.addSource(remoteDataSource.fetchEmployeesByTeamIdLD(teamId)) {
            it?.also { saveEmployees(it) }
            cachingLiveData.value = it
        }
        return cachingLiveData
    }
}