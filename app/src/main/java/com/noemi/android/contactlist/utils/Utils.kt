package com.noemi.android.contactlist.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun <T>Context.openActivity(dest: Class<T>){
    val intent = Intent(this, dest)
    startActivity(intent)
}

fun ImageView.loadPicture(uri: String){
    Glide.with(context)
            .load(uri)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
}