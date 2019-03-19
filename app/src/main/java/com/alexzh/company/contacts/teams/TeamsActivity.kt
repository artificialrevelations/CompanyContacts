package com.alexzh.company.contacts.teams

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
import com.alexzh.company.contacts.employees.EmployeesActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_teams.*

class TeamsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var viewModel: TeamsViewModel
    private val disposable = CompositeDisposable()
    private val teamsAdapter: TeamsAdapter by lazy {
        TeamsAdapter(itemClick = { EmployeesActivity.start(this, it)})
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
        disposable.add(
                viewModel.fetchTeams()
                        .subscribe(::showTeams, ::showError)
        )
        swipeRefresh.isRefreshing = true
    }

    private fun showTeams(teams: List<Team>) {
        teamsAdapter.setTeamList(teams)
        swipeRefresh.isRefreshing = false
    }

    private fun showError(error: Throwable) {
        error.printStackTrace()
        Toast.makeText(this, getString(R.string.error_message, error.cause), Toast.LENGTH_LONG).show()
        swipeRefresh.isRefreshing = false
    }

    override fun onStop() {
        disposable.dispose()
        super.onStop()
    }
}
