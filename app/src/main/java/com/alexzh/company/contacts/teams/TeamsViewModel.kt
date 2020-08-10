package com.alexzh.company.contacts.teams

import androidx.lifecycle.ViewModel
import com.alexzh.company.contacts.SchedulersProvider
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.data.source.TeamRepository
import io.reactivex.Single

class TeamsViewModel(private val teamRepository: TeamRepository,
                     private val schedulersProvider: SchedulersProvider): ViewModel() {

    fun fetchTeams(): Single<List<Team>> {
        return teamRepository.fetchTeams()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
    }
}