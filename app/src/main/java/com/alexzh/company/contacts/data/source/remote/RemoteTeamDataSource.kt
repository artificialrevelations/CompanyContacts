package com.alexzh.company.contacts.data.source.remote

import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RemoteTeamDataSource(private val apiService: ContactsApiService): TeamDataSource {

    override suspend fun fetchTeams(): Flow<List<Team>> {
        return flowOf(apiService.fetchTeams())
    }

    override suspend fun saveTeams(teams: List<Team>) {
        throw UnsupportedOperationException()
    }
}