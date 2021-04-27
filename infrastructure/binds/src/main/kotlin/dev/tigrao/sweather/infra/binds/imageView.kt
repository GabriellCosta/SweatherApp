package dev.tigrao.sweather.infra.binds

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("imageRes")
fun imageRes(imageView: ImageView, @DrawableRes imageRes: Int) {
    imageView.setImageResource(imageRes)
}
