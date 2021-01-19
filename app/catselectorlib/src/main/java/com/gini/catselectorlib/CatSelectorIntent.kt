package com.gini.catselectorlib

import android.content.Context
import android.content.Intent
import com.gini.catselectorlib.options.CatSelectorOptions
import com.gini.catselectorlib.ui.CatSelectorActivity

class CatSelectorIntent(context: Context, options: CatSelectorOptions)
    : Intent(context, CatSelectorActivity::class.java) {

    init {
        this.putCatSelectorOptionsExtra(options)
    }

    fun getOptions(): CatSelectorOptions? {
        return this.getCatSelectorOptionsExtra()
    }

    companion object {
        const val OPTIONS_PARAM = "options_param"
    }
}

fun Intent.getCatSelectorOptionsExtra(): CatSelectorOptions? {
    return this.getSerializableExtra(CatSelectorIntent.OPTIONS_PARAM) as CatSelectorOptions?
}

fun Intent.putCatSelectorOptionsExtra(options: CatSelectorOptions) {
    this.putExtra(CatSelectorIntent.OPTIONS_PARAM, options)
}