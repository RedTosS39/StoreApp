package com.example.storeapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.storeapp.di.qualificators.StoreScope
import com.example.storeapp.di.qualificators.ViewModelKey
import com.example.storeapp.presentation.viewmodel.MainViewModel
import com.example.storeapp.presentation.viewmodel.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindsMainViewModel(mainViewModel: MainViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindsShopItemViewModel(viewModel: ShopItemViewModel): ViewModel
}