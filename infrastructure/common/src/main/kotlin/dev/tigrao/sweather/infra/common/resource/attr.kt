package dev.tigrao.sweather.infra.common.resource

import android.content.Context
import android.content.res.TypedArray
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes

fun Context.getAttrDimen(@AttrRes attr: Int, @StyleRes theme: Int): Int {
    val textSizeAttr = intArrayOf(attr)
    val indexOfAttrTextSize = 0
    val a: TypedArray = obtainStyledAttributes(theme, textSizeAttr)
    val textSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1)
    return textSize
        .also {
            a.recycle()
        }
}
