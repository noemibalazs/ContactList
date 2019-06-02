package com.noemi.android.contactlist.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData



class ContactViewModel(application: Application): AndroidViewModel(application) {

    val listContacts: LiveData<List<ContactEntity>>

    init {
        val contactDB = ContactDataBase.getContactDB(application)
        listContacts = contactDB.getDAO().contactList()
    }
}
