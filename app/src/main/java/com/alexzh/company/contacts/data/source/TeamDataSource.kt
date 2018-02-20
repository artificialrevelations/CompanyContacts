package com.alexzh.company.contacts.data.source

import com.alexzh.company.contacts.data.Team

interface TeamDataSource {

    fun fetchTeams(): List<Team>
}