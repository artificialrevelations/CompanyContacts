package com.alexzh.company.contacts.teams

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alexzh.company.contacts.Injection
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.ViewModelFactory
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.employees.EmployeesActivity
import kotlinx.android.synthetic.main.activity_main.*

class TeamsActivity : AppCompatActivity() {

    private lateinit var viewModel: TeamsViewModel
    private val teamsAdapter: TeamsAdapter by lazy { TeamsAdapter(itemClick = { launch(it) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders
                .of(this, ViewModelFactory(Injection.Teams.provideTeamsViewModel(applicationContext)))
                .get(TeamsViewModel::class.java)

        teamsRecyclerView.layoutManager = LinearLayoutManager(this)
        teamsRecyclerView.adapter = teamsAdapter
    }

    private fun launch(team: Team) {
//        Toast.makeText(this, "$team", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, EmployeesActivity::class.java)
        intent.putExtra(EmployeesActivity.TEAM_ID, team.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchTeams()
                .subscribe({
                   teamsAdapter.setTeamList(it)
                }, {
                    it.printStackTrace()
                    Toast.makeText(this, "Error: ${it.cause}", Toast.LENGTH_LONG).show()
                })

    }
}
