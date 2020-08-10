package com.alexzh.company.contacts.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class Employee(
        @ColumnInfo(name = "team_id") val teamId: Long,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "photo") val photo: String,
        @ColumnInfo(name = "position") val position: String,
        @ColumnInfo(name = "phone") val phone: String) {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "employee_id") var id: Long = 0
}