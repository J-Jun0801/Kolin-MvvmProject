package com.jjg.mvvmproject.extension

import android.os.Build
import android.text.Html
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["textHtml"])
fun AppCompatTextView.textHtml(value: String?) {
    value?.let {
        this.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(value)
        }
    }
}