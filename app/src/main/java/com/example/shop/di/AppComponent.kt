package com.example.shop.di

import com.example.shop.presentation.additem.AddItemActivity
import com.example.shop.presentation.item.edit.EditItemActivity
import com.example.shop.presentation.shoplist.ShopListActivity
import com.example.shop.presentation.item.view.ViewItemActivity
import com.example.shop.presentation.login.LoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(shopListActivity: ShopListActivity)
    fun inject(addItemActivity: AddItemActivity)
    fun inject(viewItemActivity: ViewItemActivity)
    fun inject(editItemActivity: EditItemActivity)
    fun inject(loginActivity: LoginActivity)
}