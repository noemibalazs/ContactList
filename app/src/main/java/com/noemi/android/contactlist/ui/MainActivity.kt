package com.noemi.android.contactlist.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.noemi.android.contactlist.R
import com.noemi.android.contactlist.adapter.ContactAdapter
import com.noemi.android.contactlist.model.ContactDataBase
import com.noemi.android.contactlist.model.ContactViewModel
import com.noemi.android.contactlist.utils.openActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.*

class MainActivity : AppCompatActivity() {

    private var db: ContactDataBase? = null
    private var myAdapter: ContactAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        initToolbar()
        initRecycleView()

        db = ContactDataBase.getContactDB(this)
    }

    private fun initToolbar(){
        bt_add_contact.setOnClickListener {
            openActivity(AddContactActivity::class.java)
        }
    }

    private fun initRecycleView(){
        rw.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rw.setHasFixedSize(true)

        val viewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        viewModel.listContacts.observe(this, Observer {
            it?.let {
                myAdapter = ContactAdapter(this, it)
                rw.adapter = myAdapter

                if (it.isEmpty()){
                    welcome_text.visibility = View.VISIBLE
                    rw.visibility = View.GONE
                }else{
                    welcome_text.visibility = View.GONE
                    rw.visibility = View.VISIBLE
                }
            }
        })

    }

}
