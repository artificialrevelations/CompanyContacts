package com.alexzh.company.contacts.data.source.local

import androidx.room.*
import com.alexzh.company.contacts.data.Team
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TeamsDAO {

    @Query("SELECT * FROM teams")
    fun getTeams(): Single<List<Team>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam(team: Team): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeams(teams: List<Team>)

    @Update
    fun updateTeam(team: Team): Int

    @Query("DELETE FROM teams")
    fun deleteTeams()
}