package com.example.shop.di

import com.example.shop.data.repository.LoginRepositoryImpl
import com.example.shop.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module

@Module
abstract class LoginRepositoryModule {

    @Binds
    abstract fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl):
            LoginRepository
}