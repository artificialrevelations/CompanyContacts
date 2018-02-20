package com.alexzh.company.contacts.data.source

import com.alexzh.company.contacts.data.Team
import io.reactivex.Completable
import io.reactivex.Single

interface TeamDataSource {

    fun fetchTeams(): Single<List<Team>>

    fun saveTeams(teams: List<Team>): Completable
}