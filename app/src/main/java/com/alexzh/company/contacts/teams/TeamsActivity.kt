package com.alexzh.company.contacts.teams

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.ViewModelFactory
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamDataRepository
import com.alexzh.company.contacts.data.source.TeamRepository
import com.alexzh.company.contacts.data.source.local.LocalTeamDataSource
import kotlinx.android.synthetic.main.activity_main.*

class TeamsActivity : AppCompatActivity() {

    private lateinit var viewModel: TeamsViewModel
    private val teamsRepository: TeamRepository by lazy { TeamDataRepository(LocalTeamDataSource()) }
    private val teamsViewModel: TeamsViewModel by lazy { TeamsViewModel(teamsRepository) }
    private val teamsAdapter: TeamsAdapter by lazy { TeamsAdapter(itemClick = { launch(it) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(teamsViewModel)).get(TeamsViewModel::class.java)

        teamsRecyclerView.layoutManager = LinearLayoutManager(this)
        teamsRecyclerView.adapter = teamsAdapter
    }

    private fun launch(team: Team) {
        Toast.makeText(this, "$team", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        val teams = viewModel.fetchTeams()
        teamsAdapter.setTeamList(teams)
    }
}
