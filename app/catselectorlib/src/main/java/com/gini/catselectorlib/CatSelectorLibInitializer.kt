package com.gini.catselectorlib

import android.content.Context
import androidx.startup.Initializer
import com.gini.catselectorlib.di.DefaultServiceLocatorInitializer
import com.gini.catselectorlib.di.ServiceLocator

class CatSelectorLibInitializer : Initializer<ServiceLocator> {
    override fun create(context: Context): ServiceLocator {
        DefaultServiceLocatorInitializer(context)
        return ServiceLocator()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

}