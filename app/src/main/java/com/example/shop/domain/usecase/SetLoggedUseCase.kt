package com.example.shop.domain.usecase

import com.example.shop.domain.repository.LoginRepository
import javax.inject.Inject

class SetLoggedUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(isLogged: Boolean) {
        loginRepository.setLogged(isLogged)
    }
}