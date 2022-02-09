package com.example.shop.di

import android.content.Context
import android.content.SharedPreferences
import com.example.shop.App
import com.example.shop.domain.usecase.*
import com.example.shop.viewmodelfactories.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideShopListViewModelFactory(
        getListUseCase: GetListUseCase,
        isLoggedUseCase: IsLoggedUseCase,
        setLoggedUseCase: SetLoggedUseCase
    ) : ShopListViewModelFactory {
        return ShopListViewModelFactory(
            getListUseCase,
            isLoggedUseCase,
            setLoggedUseCase
        )
    }

    @Provides
    fun providePreferences(): SharedPreferences {
        return context.getSharedPreferences((context as App).packageName, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideAddItemViewModelFactory(
        createItemUseCase: CreateItemUseCase
    ) : AddItemViewModelFactory {
        return AddItemViewModelFactory(
            createItemUseCase
        )
    }

    @Provides
    fun provideEditItemViewModelFactory(
        updateItemUseCase: UpdateItemUseCase,
        getItemByIdUseCase: GetItemByIdUseCase,
        deleteItemUseCase: DeleteItemUseCase
    ) : EditItemViewModelFactory {
        return EditItemViewModelFactory(
            updateItemUseCase,
            getItemByIdUseCase,
            deleteItemUseCase
        )
    }

    @Provides
    fun provideViewItemViewModelFactory(
        getItemByIdUseCase: GetItemByIdUseCase
    ) : ViewItemViewModelFactory {
        return ViewItemViewModelFactory(
            getItemByIdUseCase
        )
    }

    @Provides
    fun provideLoginViewModelFactory(
        isAdminCredentialsUseCase: IsAdminCredentialsUseCase,
        setLoggedUseCase: SetLoggedUseCase
    ) : LoginViewModelFactory {
        return LoginViewModelFactory(
            isAdminCredentialsUseCase,
            setLoggedUseCase
        )
    }
}