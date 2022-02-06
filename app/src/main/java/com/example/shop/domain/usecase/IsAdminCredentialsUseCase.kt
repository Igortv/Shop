package com.example.shop.domain.usecase

import com.example.shop.domain.repository.LoginRepository
import javax.inject.Inject

class IsAdminCredentialsUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(login: String, password: String): Boolean {
        return loginRepository.isAdminCredentials(login, password)
    }
}