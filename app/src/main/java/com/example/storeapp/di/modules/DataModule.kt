package com.example.storeapp.di.modules

import android.app.Application
import com.example.storeapp.data.AppDatabase
import com.example.storeapp.data.RepositoryImpl
import com.example.storeapp.data.ShopListDao
import com.example.storeapp.di.qualificators.StoreScope
import com.example.storeapp.domain.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @StoreScope
    @Binds
    fun bindsRepository(repositoryImpl: RepositoryImpl): Repository

    companion object {

        @StoreScope
        @Provides
        fun providesDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}