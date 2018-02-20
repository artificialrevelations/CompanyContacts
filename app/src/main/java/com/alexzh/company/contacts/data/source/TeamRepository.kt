package com.alexzh.company.contacts.data.source

import com.alexzh.company.contacts.data.Team

interface TeamRepository {

    fun fetchTeams(): List<Team>
}