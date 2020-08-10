package com.alexzh.company.contacts.teams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.company.contacts.R
import com.alexzh.company.contacts.data.Team

class TeamsAdapter(private val itemClick: (Team) -> Unit): RecyclerView.Adapter<TeamViewHolder>() {
    private var teamList: List<Team> = listOf()

    fun setTeamList(teamList: List<Team>) {
        this.teamList = teamList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindTeam(teamList[position])
    }

    override fun getItemCount(): Int  = teamList.size
}