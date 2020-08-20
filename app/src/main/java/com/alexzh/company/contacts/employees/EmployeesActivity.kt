package com.alexzh.company.contacts.employees

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexzh.company.contacts.Injection
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.ViewModelFactory
import com.alexzh.company.contacts.common.Logger
import com.alexzh.company.contacts.common.UiState
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.Team
import kotlinx.android.synthetic.main.activity_employees.*

class EmployeesActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        const val TEAM_ID = "team_id"
        const val DEFAULT_TEAM_ID = 0L
        const val LOG_TAG = "EmployeesActivity"

        fun start(context: Context, team: Team) {
            val intent = Intent(context, EmployeesActivity::class.java)
            intent.putExtra(TEAM_ID, team.id)
            context.startActivity(intent)
        }
    }

    private lateinit var logger: Logger
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(Injection.Employees.provideEmployeesViewModel(applicationContext)))
                .get(EmployeesViewModel::class.java)
    }

    private val employeesAdapter by lazy { EmployeesAdapter(itemClick = { showDetails(it) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees)
        logger = Logger(this.lifecycle, LOG_TAG)
        employeesRecyclerView.layoutManager = LinearLayoutManager(this)
        employeesRecyclerView.adapter = employeesAdapter
        swipeRefresh.setOnRefreshListener(this)

        viewModel.employee.observe(this, Observer { handleEmployees(it) })
        refreshEmployeesByTeamId(intent.getLongExtra(TEAM_ID, DEFAULT_TEAM_ID))
    }

    private fun handleEmployees(state: UiState<List<Employee>>) {
        if (state.isLoading) {
            swipeRefresh.isRefreshing = true
        }
        if (state.data != null) {
            showEmployees(state.data)
        }
        if (state.error != null) {
            showError(state.error)
        }
    }

    private fun showDetails(employee: Employee) {
        Toast.makeText(this, "$employee", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                swipeRefresh.isRefreshing = true
                refreshEmployeesByTeamId(intent.getLongExtra(TEAM_ID, DEFAULT_TEAM_ID))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        refreshEmployeesByTeamId(intent.getLongExtra(TEAM_ID, DEFAULT_TEAM_ID))
    }

    private fun refreshEmployeesByTeamId(teamId: Long) {
        viewModel.loadEmployees(teamId)
    }

    private fun showEmployees(employees: List<Employee>) {
        employeesAdapter.setEmployeeList(employees)
        swipeRefresh.isRefreshing = false
    }

    private fun showError(error: Throwable) {
        error.printStackTrace()
        Toast.makeText(this, getString(R.string.error_message, error.cause), Toast.LENGTH_LONG).show()
        swipeRefresh.isRefreshing = false
    }
}
