package com.alexzh.company.contacts.data.source

import com.alexzh.company.contacts.data.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class TeamDataRepository(private val localDataSource: TeamDataSource,
                         private val remoteDataSource: TeamDataSource): TeamRepository {

    override suspend fun fetchTeams(): Flow<List<Team>> {
        val localData = fetchLocalTeams()
        return fetchLocalTeams()
                .flatMapLatest {
                    if (it.isEmpty()) {
                        fetchAndCacheRemoteTeams()
                    } else {
                        localData
                    }
                }
    }

    override suspend fun saveTeams(teams: List<Team>) {
        return localDataSource.saveTeams(teams)
    }

    private suspend fun fetchLocalTeams(): Flow<List<Team>> {
        return localDataSource.fetchTeams()
    }

    private suspend fun fetchAndCacheRemoteTeams(): Flow<List<Team>> {
        return remoteDataSource.fetchTeams()
                .onEach {
                    saveTeams(it)
                }
                .flowOn(Dispatchers.IO)
    }
}