package com.gini.catselectorlib.options

import java.io.Serializable

data class UIOptions(var title: CharSequence = DEFAULT_TITLE,
                     var titleStyleResId: Int = DEFAULT_TITLE_STYLE,
                     var backButtonText: CharSequence = DEFAULT_BACK_BUTTON_TEXT,
                     var backButtonStyleResId: Int = DEFAULT_BACK_BUTTON_STYLE,
                     var actionbarStyleResId: Int = DEFAULT_ACTION_BAR_STYLE) : Serializable {

    companion object {
        const val DEFAULT_TITLE = ""
        const val DEFAULT_BACK_BUTTON_TEXT = ""
        const val DEFAULT_ACTION_BAR_STYLE = 0
        const val DEFAULT_BACK_BUTTON_STYLE = 0
        const val DEFAULT_TITLE_STYLE = 0
    }
}