package com.example.storeapp.di.modules

import com.example.storeapp.di.qualificators.StoreScope
import com.example.storeapp.domain.*
import com.example.storeapp.domain.model.GetShopItemUseCase
import dagger.Binds
import dagger.Module

//@Module
//interface DomainModule {
//
//    @Binds
//    @StoreScope
//    fun bindsDeleteShopItemUseCase(repository: Repository): DeleteShopItemUseCase
//
//    @Binds
//    @StoreScope
//    fun bindsEditShopItemUseCase(repository: Repository): EditShopItemUseCase
//
//    @Binds
//    @StoreScope
//    fun bindsGetShopListUseCase(repository: Repository): GetShopListUseCase
//
//    @Binds
//    @StoreScope
//    fun bindsAddShopItemUseCase(repository: Repository): AddShopItemUseCase
//
//    @Binds
//    @StoreScope
//    fun bindsGetShopItemUseCase(repository: Repository): GetShopItemUseCase
//}