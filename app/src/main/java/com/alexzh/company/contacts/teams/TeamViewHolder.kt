package com.alexzh.company.contacts.teams

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alexzh.company.contacts.data.Team
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_team.view.*

class TeamViewHolder(view: View, private val itemClick: (Team) -> Unit): RecyclerView.ViewHolder(view) {

    fun bindTeam(team: Team) {
        with(team) {
            itemView.teamName.text = name
            itemView.setOnClickListener { itemClick(this) }

            Glide.with(itemView.context)
                    .load(logo)
                    .apply(RequestOptions.circleCropTransform())
                    .into(itemView.teamLogo)
        }
    }
}