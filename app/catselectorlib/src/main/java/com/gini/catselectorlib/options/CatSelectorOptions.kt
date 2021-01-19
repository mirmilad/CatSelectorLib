package com.gini.catselectorlib.options

import java.io.Serializable

data class CatSelectorOptions(val apiOptions: ApiOptions, val uiOptions: UIOptions = UIOptions()) : Serializable {
}