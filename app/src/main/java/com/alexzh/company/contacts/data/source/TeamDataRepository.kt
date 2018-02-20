package com.alexzh.company.contacts.data.source

import com.alexzh.company.contacts.data.Team
import io.reactivex.Completable
import io.reactivex.Single

class TeamDataRepository(private val localDataSource: TeamDataSource,
                         private val remoteDataSource: TeamDataSource): TeamRepository {

    override fun fetchTeams(): Single<List<Team>> {
//        return Single.concat(fetchLocalTeams(), fetchAndCacheRemoteTeams())
//                .filter { teams -> teams.isNotEmpty() }
//                .first(listOf())
        return fetchAndCacheRemoteTeams()
    }

    override fun saveTeams(teams: List<Team>): Completable {
        return Completable.complete()
    }

    private fun fetchLocalTeams(): Single<List<Team>> {
        return localDataSource.fetchTeams()
    }

    private fun fetchAndCacheRemoteTeams(): Single<List<Team>> {
        return remoteDataSource.fetchTeams()
//                .doOnSuccess { saveTeams(it) }
    }
}