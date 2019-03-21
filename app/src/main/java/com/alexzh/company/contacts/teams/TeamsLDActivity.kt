package com.alexzh.company.contacts.teams

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.alexzh.company.contacts.Injection
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.ViewModelFactory
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.employees.EmployeesLDActivity
import kotlinx.android.synthetic.main.activity_teams.*

class TeamsLDActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var viewModel: TeamsViewModel
    private val teamsAdapter: TeamsAdapter by lazy {
        TeamsAdapter(itemClick = { EmployeesLDActivity.start(this, it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        viewModel = ViewModelProviders
                .of(this, ViewModelFactory(Injection.Teams.provideTeamsViewModel(applicationContext)))
                .get(TeamsViewModel::class.java)

        teamsRecyclerView.layoutManager = LinearLayoutManager(this)
        teamsRecyclerView.adapter = teamsAdapter
        swipeRefresh.setOnRefreshListener(this)

        refreshTeams()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                swipeRefresh.isRefreshing = true
                refreshTeams()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRefresh() {
        refreshTeams()
    }

    private fun refreshTeams() {
        viewModel.fetchTeamsLD()
                .observe(this, teamsObserver)
        swipeRefresh.isRefreshing = true
    }

    private val teamsObserver = Observer<List<Team>> {
        it?.also { showTeams(it) }
    }

    private fun showTeams(teams: List<Team>) {
        teamsAdapter.setTeamList(teams)
        swipeRefresh.isRefreshing = false
    }
}
