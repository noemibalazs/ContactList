package com.noemi.android.contactlist.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import com.noemi.android.contactlist.R
import com.noemi.android.contactlist.model.ContactDataBase
import com.noemi.android.contactlist.model.ContactEntity
import com.noemi.android.contactlist.model.ContactExecutor
import com.noemi.android.contactlist.utils.*
import kotlinx.android.synthetic.main.add_edit_contact.*

class EditContactActivity : AppCompatActivity() {

    private var db: ContactDataBase? = null
    private var uri: Uri? = null
    private var contact: ContactEntity? = null
    private var id = 0L
    private var name = ""
    private var email = ""
    private var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit_contact)

        supportActionBar?.hide()
        initToolbar()

        db = ContactDataBase.getContactDB(this)

        val intent = intent
        intent?.let {
            id = it.getLongExtra(CONTACTS_ID, 0)
            name = it.getStringExtra(CONTACTS_NAME)
            email = it.getStringExtra(CONTACTS_EMAIL)
            phone = it.getStringExtra(CONTACTS_PHONE)
            uri = Uri.parse(it.getStringExtra(CONTACTS_URL))
        }

        edit_name_contact.setText(name)
        edit_email_contact.setText(email)
        edit_phone_contact.setText(phone)

        add_picture.setOnClickListener {
            addPicture()
        }

        save.setOnClickListener {
            if (isValid()){
                addContact()
            }else{
                return@setOnClickListener
            }
        }
    }



    private fun isValid(): Boolean{
        var valid = true

        if(TextUtils.isEmpty(edit_name_contact.text.toString())){
            edit_name_contact.setError(getString(R.string.error_message))
            valid = false
        }

        if (TextUtils.isEmpty(edit_email_contact.text.toString())){
            edit_email_contact.setError(getString(R.string.error_message))
            valid = false
        }

        if (TextUtils.isEmpty(edit_phone_contact.text.toString())){
            edit_phone_contact.setError(getString(R.string.error_message))
            valid = false
        }
        return valid
    }

    private fun addContactToDB(contact: ContactEntity){
        ContactExecutor.getExecutor().diskIO.execute {
            db?.getDAO()?.addContact(contact)
        }
    }

    private fun addContact(){
        val name = edit_name_contact.text.toString().trim()
        val email = edit_email_contact.text.toString().trim()
        val phone = edit_phone_contact.text.toString().trim()

        contact = ContactEntity(id = id, name = name, email = email, phone = phone, url = uri.toString())
        contact?.let {
            addContactToDB(it)
            finish()
        }
    }

    private fun addPicture(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.apply {
            setType("image/*")
        }
        startActivityForResult(Intent.createChooser(intent, getString(R.string.intent_title)), EDIT_PICTURE_ID)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_PICTURE_ID && resultCode == Activity.RESULT_OK && data!=null){
            uri = data.data
        }
    }

    private fun initToolbar(){
        val title = findViewById<TextView>(R.id.toolbar_title)
        title.setText(R.string.edit_contact)

        val button = findViewById<ImageView>(R.id.bt_cancel)
        button.setOnClickListener {
            finish()
        }
    }
}
