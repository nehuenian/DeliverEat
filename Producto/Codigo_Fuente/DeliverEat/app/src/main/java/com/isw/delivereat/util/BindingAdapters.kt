package com.isw.delivereat.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    /**
     * Loads an Image from an URL to an ImageView using Glide
     */
    @JvmStatic
    @BindingAdapter("imageFromURL")
    fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }

    /**
     * Sets Float as Text on TextView
     */
    @JvmStatic
    @BindingAdapter("precio")
    fun setFloat(view: TextView, value: Float) {
        if (value.isNaN()) {
            view.text = ""
        } else {
            view.text = "$ " + String.format("%.02f", value)
        }
    }

    /**
     * Sets Float as Text on TextView
     */
    @JvmStatic
    @BindingAdapter("android:text")
    fun setInt(view: TextView, value: Int) {
        view.text = value.toString()
    }
}
