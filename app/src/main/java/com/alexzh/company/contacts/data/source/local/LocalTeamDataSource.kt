package com.alexzh.company.contacts.data.source.local

import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamDataSource
import io.reactivex.Completable
import io.reactivex.Single

class LocalTeamDataSource: TeamDataSource {
    override fun fetchTeams(): Single<List<Team>> {
        return Single.just(listOf())
    }

    override fun saveTeams(teams: List<Team>): Completable {
        throw UnsupportedOperationException()
    }
}