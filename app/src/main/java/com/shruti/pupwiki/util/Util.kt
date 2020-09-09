package com.shruti.pupwiki.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shruti.pupwiki.R

// Spinner img while the url image is being loaded
fun getProgressDrawable(context: Context) : CircularProgressDrawable{

    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_puppy)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view : ImageView, url : String?){
    view.loadImage(url, getProgressDrawable(view.context))
}
@BindingAdapter("android:visibility")
fun setVisibility(view: TextView, vale:String?){
    view.setVisibility(if(vale.isNullOrEmpty()) View.GONE else View.VISIBLE)
}