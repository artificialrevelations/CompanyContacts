package com.alexzh.company.contacts.data.source.local

import androidx.room.*
import com.alexzh.company.contacts.data.Employee
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeesDAO {

    @Query("SELECT * FROM employees WHERE team_id = :teamId")
    fun getEmployeesByTeamId(teamId: Long): Flow<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(employee: Employee)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(teams: List<Employee>)

    @Update
    fun updateEmployee(employee: Employee): Int

    @Delete
    fun deleteEmployee(employee: Employee): Int

    @Query("DELETE FROM employees")
    fun deleteEmployees()
}