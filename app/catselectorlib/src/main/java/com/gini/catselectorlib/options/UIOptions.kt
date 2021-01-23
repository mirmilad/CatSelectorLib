package com.gini.catselectorlib.options

import java.io.Serializable

data class UIOptions(var title: CharSequence? = DEFAULT_TITLE,
                     var backButtonText: CharSequence? = DEFAULT_BACK_BUTTON_TEXT,
                     var theme: Int = DEFAULT_THEME) : Serializable {

    companion object {
        val DEFAULT_TITLE = null
        val DEFAULT_BACK_BUTTON_TEXT = null
        const val DEFAULT_THEME = 0
    }
}