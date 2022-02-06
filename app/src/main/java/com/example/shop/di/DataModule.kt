package com.example.shop.di

import android.content.SharedPreferences
import com.example.shop.Constants
import com.example.shop.data.remote.ShopApi
import com.example.shop.data.repository.LoginRepositoryImpl
import com.example.shop.data.repository.ShopRepositoryImpl
import com.example.shop.domain.repository.LoginRepository
import com.example.shop.domain.repository.ShopRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideShopRepository(shopApi: ShopApi): ShopRepository {
        return ShopRepositoryImpl(shopApi)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(sharedPreferences: SharedPreferences): LoginRepository {
        return LoginRepositoryImpl(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideShopApi(): ShopApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShopApi::class.java)
    }
}