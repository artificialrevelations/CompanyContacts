package com.alexzh.company.contacts.teams

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.alexzh.company.contacts.SchedulersProvider
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamRepository
import io.reactivex.Single

class TeamsViewModel(private val teamRepository: TeamRepository,
                     private val schedulersProvider: SchedulersProvider): ViewModel() {

    fun fetchTeamsRx(): Single<List<Team>> {
        return teamRepository.fetchTeamsRx()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
    }

    fun fetchTeamsLD(): LiveData<List<Team>> {
        return teamRepository.fetchTeamsLD()
    }
}