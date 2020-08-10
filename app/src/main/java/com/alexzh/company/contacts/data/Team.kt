package com.alexzh.company.contacts.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
        @PrimaryKey @ColumnInfo(name = "team_id") var id: Long,
        @ColumnInfo(name = "title") val name: String,
        @ColumnInfo(name = "logo") val logo: String
)