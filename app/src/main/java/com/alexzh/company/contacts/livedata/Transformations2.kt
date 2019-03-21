package com.alexzh.company.contacts.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread

object Transformations2 {
    @MainThread
    fun <X> concat(vararg sources: LiveData<X>): LiveData<X> {
        val concatLiveData = MediatorLiveData<X>()
        sources.forEach {
            concatLiveData.addSource(it) { value ->
                concatLiveData.value = value
            }
        }
        return concatLiveData
    }

    @MainThread
    fun <X> filter(source: LiveData<X>, predicate: (X) -> Boolean): LiveData<X> {
        return MediatorLiveData<X>().also { mediator ->
            mediator.addSource(source) { value ->
                value?.also {
                    if (predicate(it))
                        mediator.value = it
                }
            }
        }
    }
}