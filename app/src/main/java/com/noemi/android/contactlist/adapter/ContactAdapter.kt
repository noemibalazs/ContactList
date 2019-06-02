package com.noemi.android.contactlist.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.noemi.android.contactlist.R
import com.noemi.android.contactlist.model.ContactDataBase
import com.noemi.android.contactlist.model.ContactEntity
import com.noemi.android.contactlist.model.ContactExecutor
import com.noemi.android.contactlist.ui.EditContactActivity
import com.noemi.android.contactlist.utils.*
import kotlinx.android.synthetic.main.item_contact.view.*
import kotlinx.android.synthetic.main.pop_up_delete.view.*

class ContactAdapter(var context: Context, val contactList:List<ContactEntity>): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var db = ContactDataBase.getContactDB(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact, viewGroup, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, index: Int) {

        val contact = contactList[index]
        holder.contactName.text = contact.name
        holder.contactEmail.text = contact.email
        holder.contactPhoneNumber.text = contact.phone
        val pictureUri = contact.url

        pictureUri?.let {
            holder.contactInitials.contactImage.loadPicture(it)
        }

        holder.contactInitials.nameInitials.text = contact.name.take(1).toUpperCase()

        holder.contactMenuOptions.setOnClickListener {

            val view = LayoutInflater.from(context).inflate(R.layout.pop_up, null)
            val popUpWindow = PopupWindow()
            popUpWindow.contentView = view
            showPopupWindow(popUpWindow)
            popUpWindow.showAsDropDown(holder.contactMenuOptions, 0, -125)

            val editContact = view.findViewById<LinearLayout>(R.id.layout_edit_update)
            editContact?.setOnClickListener {

                openEditDeleteActivity(contact, popUpWindow)
            }
            val deleteContact = view.findViewById<LinearLayout>(R.id.layout_delete_contact)
            deleteContact?.setOnClickListener {

                deleteDialog(db, popUpWindow, contact)
            }
        }

    }

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var contactInitials = view.contact_image_initials
        var contactName = view.contact_name
        var contactEmail = view.contact_email
        var contactPhoneNumber = view.contact_phone
        var contactMenuOptions = view.contact_edit_delete
    }

    private fun openEditDeleteActivity(contact: ContactEntity, popUp: PopupWindow) {
        val intent = Intent(context, EditContactActivity::class.java)
        intent.apply {
            putExtra(CONTACTS_ID, contact.id)
            putExtra(CONTACTS_NAME, contact.name)
            putExtra(CONTACTS_EMAIL, contact.email)
            putExtra(CONTACTS_PHONE, contact.phone)
            putExtra(CONTACTS_URL, contact.url)
        }
        context.startActivity(intent)
        popUp.dismiss()
    }

    private fun showPopupWindow(popUp: PopupWindow) {
        popUp.width = ViewGroup.LayoutParams.MATCH_PARENT
        popUp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popUp.animationStyle = R.style.PopUpAnimationStyle
        popUp.isOutsideTouchable = true
        popUp.isFocusable = true
    }

    private fun deleteDialog(db: ContactDataBase, popUp: PopupWindow, contact: ContactEntity) {

        val view = LayoutInflater.from(context).inflate(R.layout.pop_up_delete, null)
        val builder = AlertDialog.Builder(context).create()
        builder.setView(view)
        val delete = view.delete_delete
        delete.setOnClickListener {
            ContactExecutor.getExecutor().diskIO.execute {
                db.getDAO().deleteContact(contact)
            }
            builder.dismiss()
            popUp.dismiss()
        }
        val dismiss = view.delete_dismiss
        dismiss.setOnClickListener {
            builder.dismiss()
            popUp.dismiss()
        }
        builder.show()
    }

}