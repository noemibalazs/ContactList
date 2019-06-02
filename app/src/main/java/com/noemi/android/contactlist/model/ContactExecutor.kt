package com.noemi.android.contactlist.model

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ContactExecutor private constructor(val diskIO: Executor, val networkIO:Executor, val mainThread: Executor){

    private class MainTreadExecutor : Executor{

        private var handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            handler.post(command)
        }
    }

    companion object {

        private var sInstance: ContactExecutor? = null
        private var LOCK = Any()

        fun getExecutor(): ContactExecutor{

            if (sInstance == null){
                synchronized(LOCK){
                    sInstance = ContactExecutor(Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(3),
                            MainTreadExecutor())
                }
            }
            return sInstance!!
        }
    }
}