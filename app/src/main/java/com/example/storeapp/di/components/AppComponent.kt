package com.example.storeapp.di.components

import android.app.Application
import com.example.storeapp.di.modules.DataModule
import com.example.storeapp.di.modules.ViewModelModule
import com.example.storeapp.di.qualificators.StoreScope
import com.example.storeapp.presentation.view.MainActivity
import com.example.storeapp.presentation.view.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@StoreScope
@Component(modules = [
    DataModule::class,
    ViewModelModule::class
])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application) : AppComponent
    }
}