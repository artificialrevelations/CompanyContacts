package com.alexzh.company.contacts.data.source

import android.arch.lifecycle.LiveData
import com.alexzh.company.contacts.data.Team
import io.reactivex.Completable
import io.reactivex.Single

interface TeamRepository {

    fun fetchTeamsRx(): Single<List<Team>>

    fun fetchTeamsLD(): LiveData<List<Team>>

    fun saveTeams(teams: List<Team>): Completable
}