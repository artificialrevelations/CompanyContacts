package com.alexzh.company.contacts.employees

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alexzh.company.contacts.Injection
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.ViewModelFactory
import com.alexzh.company.contacts.data.Employee
import kotlinx.android.synthetic.main.activity_employees.*

class EmployeesActivity : AppCompatActivity() {

    companion object {
        const val TEAM_ID = "team_id"
    }

    private lateinit var viewModel: EmployeesViewModel
    private val employeesAdapter: EmployeesAdapter by lazy { EmployeesAdapter(itemClick = { launch(it) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees)

        viewModel = ViewModelProviders
                .of(this, ViewModelFactory(Injection.Employees.provideEmployeesViewModel(applicationContext)))
                .get(EmployeesViewModel::class.java)

        employeesRecyclerView.layoutManager = LinearLayoutManager(this)
        employeesRecyclerView.adapter = employeesAdapter
    }

    private fun launch(employee: Employee) {
        Toast.makeText(this, "$employee", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        val teamId = intent.getLongExtra(TEAM_ID, 0)
        viewModel.fetchEmployeesById(teamId)
                .subscribe({
                    employeesAdapter.setEmployeeList(it)
                }, {
                    it.printStackTrace()
                    Toast.makeText(this, "Error: ${it.cause}", Toast.LENGTH_LONG).show()
                })

    }
}
