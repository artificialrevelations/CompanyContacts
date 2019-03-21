package com.alexzh.company.contacts.data.source.remote

import android.arch.lifecycle.LiveData
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.Team
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactsApiService {

    @GET("teams.json")
    fun fetchTeamsRx(): Single<List<Team>>

    @GET("teams.json")
    fun fetchTeamsLD(): LiveData<List<Team>>

    @GET("team-{teamId}.json")
    fun fetchEmployeesByTeamIdRx(@Path("teamId") teamId: Long): Single<List<Employee>>

    @GET("team-{teamId}.json")
    fun fetchEmployeesByTeamIdLD(@Path("teamId") teamId: Long): LiveData<List<Employee>>
}