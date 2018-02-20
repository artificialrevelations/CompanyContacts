package com.alexzh.company.contacts.teams

import android.support.v7.widget.RecyclerView
import android.view.View
import com.alexzh.company.contacts.data.Team
import kotlinx.android.synthetic.main.item_team.view.*

class TeamViewHolder(view: View, private val itemClick: (Team) -> Unit): RecyclerView.ViewHolder(view) {

    fun bindTeam(team: Team) {
        with(team) {
            itemView.teamName.text = name
            itemView.setOnClickListener { itemClick(this) }
        }
    }
}