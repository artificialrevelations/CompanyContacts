package com.alexzh.company.contacts

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun ui(): Scheduler

    fun io(): Scheduler
}