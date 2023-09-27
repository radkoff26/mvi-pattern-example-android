package com.github.radkoff26.mvi.interfaces

import android.text.Editable
import android.text.TextWatcher

interface DefaultTextWatcher: TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

    }
}