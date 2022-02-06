package com.example.shop

import android.app.Application
import com.example.shop.di.AppComponent
import com.example.shop.di.AppModule
import com.example.shop.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}