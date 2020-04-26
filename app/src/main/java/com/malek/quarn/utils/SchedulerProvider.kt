package com.malek.quarn.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


object SchedulerProvider {

    private var isForTesting = false
    fun init(testing: Boolean = false) {
        isForTesting = testing
    }

    fun getIo(): Scheduler = if (isForTesting) Schedulers.trampoline() else Schedulers.io()
}