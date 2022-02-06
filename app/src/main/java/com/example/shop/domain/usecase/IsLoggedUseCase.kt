package com.example.shop.domain.usecase

import com.example.shop.domain.repository.LoginRepository
import javax.inject.Inject

class IsLoggedUseCase @Inject constructor(
    private val loginRepository: LoginRepository
){
    operator fun invoke(): Boolean {
        return loginRepository.isLogged()
    }
}