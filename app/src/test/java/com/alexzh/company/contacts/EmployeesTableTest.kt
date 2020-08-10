package com.alexzh.company.contacts

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.local.ContactsDatabase
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EmployeesTableTest {

    private val database: ContactsDatabase by lazy {
        val context: Context = ApplicationProvider.getApplicationContext()
        Room.inMemoryDatabaseBuilder(context, ContactsDatabase::class.java)
                .allowMainThreadQueries() //only for testing
                .build()
    }

    @Test
    fun shouldAddEmployeeToDatabase() {
        val team = Team(
                id = 1,
                name = "Test team #1",
                logo = ""
        )
        val employee = Employee(
                teamId = team.id,
                name = "Test Employee",
                photo = "",
                position = "Android Dev",
                phone = "111-222-333"
        )

        database.teamsDao().insertTeam(team).test()
        database.employeesDao().insertEmployee(employee).test()

        val employees: Single<List<Employee>> = database.employeesDao().getEmployeesByTeamId(team.id)

        employees.test()
            .assertValue(listOf(employee))
    }
}