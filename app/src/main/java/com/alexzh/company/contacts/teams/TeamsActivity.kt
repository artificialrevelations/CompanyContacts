package com.alexzh.company.contacts.teams

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alexzh.company.contacts.Injection
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.ViewModelFactory
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.employees.EmployeesActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class TeamsActivity : AppCompatActivity() {

    private lateinit var viewModel: TeamsViewModel
    private val disposable = CompositeDisposable()
    private val teamsAdapter: TeamsAdapter by lazy {
        TeamsAdapter(itemClick = { EmployeesActivity.start(this, it)})
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders
                .of(this, ViewModelFactory(Injection.Teams.provideTeamsViewModel(applicationContext)))
                .get(TeamsViewModel::class.java)

        teamsRecyclerView.layoutManager = LinearLayoutManager(this)
        teamsRecyclerView.adapter = teamsAdapter
    }

    override fun onStart() {
        super.onStart()
        disposable.add(
                viewModel.fetchTeams()
                    .subscribe(::showTeams, ::showError)
        )
    }

    private fun showTeams(teams: List<Team>) {
        teamsAdapter.setTeamList(teams)
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
