package com.example.shop.domain.repository

interface LoginRepository {
    fun isLogged(): Boolean
    fun setLogged(isLogged: Boolean)
    fun isAdminCredentials(login: String, password: String): Boolean
}