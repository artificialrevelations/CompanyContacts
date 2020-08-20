package com.alexzh.company.contacts.data.source.remote

import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.Team
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactsApiService {

    @GET("teams.json")
    suspend fun fetchTeams(): List<Team>

    @GET("team-{teamId}.json")
    suspend fun fetchEmployeesByTeamId(@Path("teamId") teamId: Long): List<Employee>
}