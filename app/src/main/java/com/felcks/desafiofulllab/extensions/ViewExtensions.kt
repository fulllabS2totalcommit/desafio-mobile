package com.felcks.desafiofulllab.extensions

import androidx.databinding.BindingAdapter
import android.view.View

@set:BindingAdapter("bind:isVisible")
inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }