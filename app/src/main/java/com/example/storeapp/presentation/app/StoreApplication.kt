package com.example.storeapp.presentation.app

import android.app.Application
import com.example.storeapp.di.components.DaggerAppComponent

class StoreApplication : Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}