package com.noemi.android.contactlist.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.noemi.android.contactlist.R

class ImagePlaceHolder @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, def: Int=0): FrameLayout(context, attrs, def) {

    val view = View.inflate(context, R.layout.frame_holder, this)
    val contactImage = view.findViewById<ImageView>(R.id.image_holder)
    val nameInitials = view.findViewById<TextView>(R.id.name_holder)
}