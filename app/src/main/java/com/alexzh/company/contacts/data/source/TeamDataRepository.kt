package com.alexzh.company.contacts.data.source

import com.alexzh.company.contacts.data.Team

class TeamDataRepository(private val localDataSource: TeamDataSource): TeamRepository {

    override fun fetchTeams(): List<Team> {
        return localDataSource.fetchTeams()
    }
}