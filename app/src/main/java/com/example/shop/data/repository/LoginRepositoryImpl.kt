package com.example.shop.data.repository

import android.content.SharedPreferences
import com.example.shop.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : LoginRepository {
    companion object {
        const val IS_LOGGED_PREF = "is_logged"
        const val ADMIN_LOGIN = "admin"
        const val ADMIN_PASSWORD = "admin"
    }

    override fun isLogged(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_PREF, false)
    }

    override fun setLogged(isLogged: Boolean) {
        sharedPreferences.edit().putBoolean(IS_LOGGED_PREF, isLogged).apply()
    }

    override fun isAdminCredentials(login: String, password: String): Boolean {
        return login == ADMIN_LOGIN && password == ADMIN_PASSWORD
    }
}