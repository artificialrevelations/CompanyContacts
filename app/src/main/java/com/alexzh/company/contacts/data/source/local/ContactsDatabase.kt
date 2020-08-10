package com.alexzh.company.contacts.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alexzh.company.contacts.data.Employee
import com.alexzh.company.contacts.data.Team

@Database(entities = [Team::class, Employee::class], version = 1)
abstract class ContactsDatabase: RoomDatabase() {

    abstract fun teamsDao(): TeamsDAO

    abstract fun employeesDao(): EmployeesDAO

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
                            .addCallback(DB_CALLBACK)
                            .build()
                }
                return INSTANCE!!
            }
        }

        private val DB_CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("CREATE TABLE IF NOT EXISTS wordcount(" +
                        "  word TEXT PRIMARY KEY," +
                        "  cnt INTEGER" +
                        ") WITHOUT ROWID;")
            }
        }
    }
}