package com.alexzh.company.contacts.employees

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alexzh.company.contacts.Injection
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.ViewModelFactory
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.Team
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_employees.*

class EmployeesActivity : AppCompatActivity() {

    companion object {
        const val TEAM_ID = "team_id"

        fun start(context: Context, team: Team) {
            val intent = Intent(context, EmployeesActivity::class.java)
            intent.putExtra(EmployeesActivity.TEAM_ID, team.id)
            context.startActivity(intent)
        }
    }

    private val disposable = CompositeDisposable()
    private lateinit var viewModel: EmployeesViewModel
    private val employeesAdapter: EmployeesAdapter by lazy { EmployeesAdapter(itemClick = { showDetails(it) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees)

        viewModel = ViewModelProviders
                .of(this, ViewModelFactory(Injection.Employees.provideEmployeesViewModel(applicationContext)))
                .get(EmployeesViewModel::class.java)

        employeesRecyclerView.layoutManager = LinearLayoutManager(this)
        employeesRecyclerView.adapter = employeesAdapter
    }

    private fun showDetails(employee: Employee) {
        Toast.makeText(this, "$employee", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()

        val teamId = intent.getLongExtra(TEAM_ID, 0)
        disposable.add(
                viewModel.fetchEmployeesById(teamId)
                    .subscribe(::showEmployees, ::showError)
        )
    }

    private fun showEmployees(employees: List<Employee>) {
        employeesAdapter.setEmployeeList(employees)
    }

    private fun showError(error: Throwable) {
        error.printStackTrace()
        Toast.makeText(this, getString(R.string.error_message, error.cause), Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }
}
