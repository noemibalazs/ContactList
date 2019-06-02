package com.noemi.android.contactlist.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [ContactEntity::class], version = 1, exportSchema = false)
abstract class ContactDataBase : RoomDatabase(){

    companion object {

        private var INSTANCE : ContactDataBase? = null
        private var LOCK = Any()

        fun getContactDB(context: Context): ContactDataBase{

            if (INSTANCE == null){
                synchronized(LOCK){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ContactDataBase::class.java,
                            "contact.db").build()
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun getDAO(): ContactDAO
}