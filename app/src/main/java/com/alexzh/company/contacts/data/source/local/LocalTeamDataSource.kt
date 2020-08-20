package com.alexzh.company.contacts.data.source.local

import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamDataSource
import kotlinx.coroutines.flow.Flow

class LocalTeamDataSource(private val database: ContactsDatabase): TeamDataSource {
    override suspend fun fetchTeams(): Flow<List<Team>> {
        return database.teamsDao().getTeams()
    }

    override suspend fun saveTeams(teams: List<Team>) {
        database.teamsDao().insertTeams(teams)
    }
}