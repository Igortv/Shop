package com.example.shop.di

import com.example.shop.data.repository.ShopRepositoryImpl
import com.example.shop.domain.repository.ShopRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ShopRepositoryModule {

    @Binds
    abstract fun provideShopRepository(shopRepositoryImpl: ShopRepositoryImpl):
            ShopRepository
}