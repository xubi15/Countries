package com.example.countries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.countries.R
import com.squareup.picasso.Picasso

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    Picasso.get()
        .load(uri)
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
        .into(this)
}