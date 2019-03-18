package com.alexzh.company.contacts.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "employees")
data class Employee(
        @ColumnInfo(name = "team_id") val teamId: Long,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "photo") val photo: String,
        @ColumnInfo(name = "position") val position: String,
        @ColumnInfo(name = "phone") val phone: String) {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "employee_id") var id: Long = 0
}