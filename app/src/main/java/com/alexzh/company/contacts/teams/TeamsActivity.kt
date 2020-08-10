package com.alexzh.company.contacts.teams

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alexzh.company.contacts.Injection
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.ViewModelFactory
import com.alexzh.company.contacts.common.Logger
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.employees.EmployeesActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_teams.*

class TeamsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    companion object {
        private const val LOG_TAG = "TeamsActivity"
    }

    private lateinit var logger: Logger
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(Injection.Teams.provideTeamsViewModel(applicationContext)))
            .get(TeamsViewModel::class.java)
    }
    private val disposable = CompositeDisposable()
    private val teamsAdapter by lazy { TeamsAdapter(itemClick = { EmployeesActivity.start(this, it)}) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        logger = Logger(this.lifecycle, LOG_TAG)

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
