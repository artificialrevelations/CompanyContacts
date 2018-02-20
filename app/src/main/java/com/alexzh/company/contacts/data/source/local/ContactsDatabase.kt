package com.alexzh.company.contacts.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.alexzh.company.contacts.data.Team

@Database(entities = [(Team::class)], version = 1)
abstract class ContactsDatabase: RoomDatabase() {

    abstract fun teamsDao(): TeamsDAO

    companion object {
        private const val DATABASE_NAME = "contacts.db"
        private var INSTANCE: ContactsDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): ContactsDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ContactsDatabase::class.java,
                            DATABASE_NAME)
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}