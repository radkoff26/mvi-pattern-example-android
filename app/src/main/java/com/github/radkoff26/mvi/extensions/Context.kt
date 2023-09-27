package com.github.radkoff26.mvi.extensions

import android.content.Context
import android.widget.Toast

fun Context.toastMessage(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
