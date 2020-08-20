package com.alexzh.company.contacts.data.source.local

import androidx.room.*
import com.alexzh.company.contacts.data.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamsDAO {

    @Query("SELECT * FROM teams")
    fun getTeams(): Flow<List<Team>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam(team: Team)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeams(teams: List<Team>)

    @Update
    fun updateTeam(team: Team): Int

    @Query("DELETE FROM teams")
    fun deleteTeams()
}