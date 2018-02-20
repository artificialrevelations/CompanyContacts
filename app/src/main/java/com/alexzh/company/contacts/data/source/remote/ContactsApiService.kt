package com.alexzh.company.contacts.data.source.remote

import com.alexzh.company.contacts.data.Team
import io.reactivex.Single
import retrofit2.http.GET

interface ContactsApiService {

    @GET("teams.json")
    fun fetchTeams(): Single<List<Team>>
}