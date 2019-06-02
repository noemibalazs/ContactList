package com.noemi.android.contactlist.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ContactDAO {

    @Query("SELECT * FROM contact_list")
    fun contactList(): LiveData<List<ContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addContact(lead: ContactEntity)

    @Delete
    fun deleteContact(lead: ContactEntity)
}