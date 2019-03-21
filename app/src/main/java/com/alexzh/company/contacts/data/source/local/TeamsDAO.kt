package com.alexzh.company.contacts.data.source.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.alexzh.company.contacts.data.Team
import io.reactivex.Single

@Dao
interface TeamsDAO {

    @Query("SELECT * FROM teams")
    fun getTeamsRx(): Single<List<Team>>

    @Query("SELECT * FROM teams")
    fun getTeamsLD(): LiveData<List<Team>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam(team: Team)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeams(teams: List<Team>)

    @Update
    fun updateTeam(team: Team): Int

    @Query("DELETE FROM teams")
    fun deleteTeams()
}