package com.alexzh.company.contacts.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class TeamsViewModel(
        private val teamRepository: TeamRepository
): ViewModel() {

    suspend fun fetchTeams(): Flow<List<Team>> {
        (1..10).asFlow().asLiveData()

        return teamRepository.fetchTeams()
    }
}