package com.alexzh.company.contacts.data.source

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.alexzh.company.contacts.data.Team
import com.alexzh.company.contacts.livedata.Transformations2
import io.reactivex.Completable
import io.reactivex.Single

class TeamDataRepository(private val localDataSource: TeamDataSource,
                         private val remoteDataSource: TeamDataSource) : TeamRepository {
    override fun fetchTeamsRx(): Single<List<Team>> {
        return Single.concat(fetchLocalTeamsRx(), fetchAndCacheRemoteTeamsRx())
                .filter { teams -> teams.isNotEmpty() }
                .first(listOf())
    }

    override fun fetchTeamsLD(): LiveData<List<Team>> {
        /*
          With the use of the MediatorLiveData we can "concat" any amount of LiveData sources.
          As LiveData is not supposed to be a Rx observable replacement and it is not working
          in the same way it is hard to match it on the concepts of "completing" a stream etc.
         */
        val concatLiveData = Transformations2.concat(
                fetchLocalTeamsLD(), fetchAndCacheRemoteTeamsLD()
        )

        //TODO: Single LiveData (LiveData that sends once and never more?)
        return Transformations2.filter(concatLiveData) {
            it.isNotEmpty()
        }
    }

    override fun saveTeams(teams: List<Team>): Completable {
        return localDataSource.saveTeams(teams)
    }

    private fun fetchLocalTeamsRx(): Single<List<Team>> {
        return localDataSource.fetchTeamsRx()
    }

    private fun fetchLocalTeamsLD(): LiveData<List<Team>> {
        return localDataSource.fetchTeamsLD()
    }

    private fun fetchAndCacheRemoteTeamsRx(): Single<List<Team>> {
        return remoteDataSource.fetchTeamsRx()
                .doOnSuccess { saveTeams(it) }
    }

    private fun fetchAndCacheRemoteTeamsLD(): LiveData<List<Team>> {
        val cachingLiveData = MediatorLiveData<List<Team>>()
        cachingLiveData.addSource(remoteDataSource.fetchTeamsLD()) {
            it?.also { saveTeams(it) }
            cachingLiveData.value = it
        }
        return cachingLiveData
    }
}