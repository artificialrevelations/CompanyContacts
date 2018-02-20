package com.alexzh.company.contacts.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
        @PrimaryKey @ColumnInfo(name = "team_id") var id: Long,
        @ColumnInfo(name = "title") val name: String)