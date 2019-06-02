package com.noemi.android.contactlist.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "contact_list")
data class ContactEntity(@PrimaryKey (autoGenerate = true) var id: Long = 0,
                         var name: String,
                         var email: String,
                         var phone: String,
                         var url: String? = null)