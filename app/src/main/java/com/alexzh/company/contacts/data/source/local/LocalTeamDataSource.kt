package com.alexzh.company.contacts.data.source.local

import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamDataSource

class LocalTeamDataSource: TeamDataSource {

    override fun fetchTeams(): List<Team> {
        return listOf(
                Team("Andromeda"),
                Team("Orion"),
                Team("Phoenix"))
    }
}