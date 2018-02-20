package com.alexzh.company.contacts

import android.app.Application
import com.facebook.stetho.Stetho

class ContactsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}