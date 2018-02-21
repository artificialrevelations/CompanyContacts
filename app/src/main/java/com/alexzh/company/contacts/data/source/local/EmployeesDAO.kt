package com.alexzh.company.contacts.data.source.local

import android.arch.persistence.room.*
import com.alexzh.company.contacts.data.Employee
import io.reactivex.Single

@Dao
interface EmployeesDAO {

    @Query("SELECT * FROM employees WHERE team_id = :teamId")
    fun getEmployeesByTeamId(teamId: Long): Single<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(employee: Employee)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(teams: List<Employee>)

    @Update
    fun updateEmployee(employee: Employee): Int

    @Query("DELETE FROM employees")
    fun deleteEmployees()
}