package com.alexzh.company.contacts.data.source.remote

import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.Team
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactsApiService {

    @GET("teams.json")
    fun fetchTeams(): Single<List<Team>>

    @GET("team-{teamId}.json")
    fun fetchEmployeesByTeamId(@Path("teamId") teamId: Long): Single<List<Employee>>
}