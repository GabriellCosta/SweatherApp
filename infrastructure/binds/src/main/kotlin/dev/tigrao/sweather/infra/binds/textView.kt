package dev.tigrao.sweather.infra.binds

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("nativeString")
fun nativeString(view: TextView, nativeString: NativeStringType?) {
    if (nativeString?.raw != null)
        view.text = nativeString.raw

    if (nativeString?.res != null)
        view.setText(nativeString.res)
}
