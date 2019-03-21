package com.alexzh.company.contacts.data.source.local

import android.arch.lifecycle.LiveData
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamDataSource
import io.reactivex.Completable
import io.reactivex.Single

class LocalTeamDataSource(private val database: ContactsDatabase) : TeamDataSource {

    override fun fetchTeamsRx(): Single<List<Team>> {
        return database.teamsDao().getTeamsRx()
    }

    override fun fetchTeamsLD(): LiveData<List<Team>> {
        return database.teamsDao().getTeamsLD()
    }

    override fun saveTeams(teams: List<Team>): Completable {
        database.teamsDao().insertTeams(teams)
        return Completable.complete()
    }
}