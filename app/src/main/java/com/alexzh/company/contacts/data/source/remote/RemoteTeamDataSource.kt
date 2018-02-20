package com.alexzh.company.contacts.data.source.remote

import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamDataSource
import io.reactivex.Completable
import io.reactivex.Single

class RemoteTeamDataSource(private val apiService: ContactsApiService): TeamDataSource {

    override fun fetchTeams(): Single<List<Team>> {
        return apiService.fetchTeams()
    }

    override fun saveTeams(teams: List<Team>): Completable {
        throw UnsupportedOperationException()
    }
}