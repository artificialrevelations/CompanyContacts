package com.alexzh.company.contacts.data.source.local

import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamDataSource
import io.reactivex.Completable
import io.reactivex.Single

class LocalTeamDataSource(private val database: ContactsDatabase): TeamDataSource {
    override fun fetchTeams(): Single<List<Team>> {
        return database.teamsDao().getTeams()
    }

    override fun saveTeams(teams: List<Team>): Completable {
        database.teamsDao().insertTeams(teams)
        return Completable.complete()
    }
}