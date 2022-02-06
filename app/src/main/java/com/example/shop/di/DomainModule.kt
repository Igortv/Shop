package com.example.shop.di

import com.example.shop.domain.repository.ShopRepository
import com.example.shop.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetListUseCase(shopRepository: ShopRepository): GetListUseCase {
        return GetListUseCase(shopRepository)
    }

    @Provides
    fun provideCreateItemUseCase(shopRepository: ShopRepository): CreateItemUseCase {
        return CreateItemUseCase(shopRepository)
    }

    @Provides
    fun provideGetItemByIdUseCase(shopRepository: ShopRepository): GetItemByIdUseCase {
        return GetItemByIdUseCase(shopRepository)
    }

    @Provides
    fun provideUpdateItemUseCase(shopRepository: ShopRepository): UpdateItemUseCase {
        return UpdateItemUseCase(shopRepository)
    }

    @Provides
    fun provideDeleteItemUseCase(shopRepository: ShopRepository): DeleteItemUseCase {
        return DeleteItemUseCase(shopRepository)
    }
}