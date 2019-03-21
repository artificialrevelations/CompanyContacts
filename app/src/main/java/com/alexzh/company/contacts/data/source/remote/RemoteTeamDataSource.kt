package com.alexzh.company.contacts.data.source.remote

import android.arch.lifecycle.LiveData
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamDataSource
import io.reactivex.Completable
import io.reactivex.Single

class RemoteTeamDataSource(private val apiService: ContactsApiService): TeamDataSource {
    override fun fetchTeamsRx(): Single<List<Team>> {
        return apiService.fetchTeamsRx()
    }

    override fun fetchTeamsLD(): LiveData<List<Team>> {
        return apiService.fetchTeamsLD()
    }

    override fun saveTeams(teams: List<Team>): Completable {
        throw UnsupportedOperationException()
    }
}