package com.alexzh.company.contacts.teams

import android.arch.lifecycle.ViewModel
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamRepository

class TeamsViewModel(private val teamRepository: TeamRepository): ViewModel() {

    fun fetchTeams(): List<Team> {
        return teamRepository.fetchTeams()
    }
}