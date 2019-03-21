package com.alexzh.company.contacts.employees

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.alexzh.company.contacts.Injection
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.ViewModelFactory
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.Team
import kotlinx.android.synthetic.main.activity_employees.*

class EmployeesLDActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TEAM_ID = "team_id"
        const val DEFAULT_TEAM_ID = 0L

        fun start(context: Context, team: Team) {
            val intent = Intent(context, EmployeesLDActivity::class.java)
            intent.putExtra(EmployeesLDActivity.TEAM_ID, team.id)
            context.startActivity(intent)
        }
    }

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
        swipeRefresh.setOnRefreshListener(this)

        refreshEmployeesByTeamId(intent.getLongExtra(TEAM_ID, DEFAULT_TEAM_ID))
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
        viewModel.fetchEmployeesByIdLD(teamId)
                .observe(this, employeesObserver)
        swipeRefresh.isRefreshing = true
    }

    private val employeesObserver = Observer<List<Employee>> {
        it?.also { showEmployees(it) }
    }

    private fun showEmployees(employees: List<Employee>) {
        employeesAdapter.setEmployeeList(employees)
        swipeRefresh.isRefreshing = false
    }
}
