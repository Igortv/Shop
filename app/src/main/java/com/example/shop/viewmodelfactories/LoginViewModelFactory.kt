package com.example.shop.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.usecase.GetItemByIdUseCase
import com.example.shop.domain.usecase.IsAdminCredentialsUseCase
import com.example.shop.domain.usecase.SetLoggedUseCase
import com.example.shop.presentation.item.view.ViewItemViewModel
import com.example.shop.presentation.login.LoginViewModel

class LoginViewModelFactory(
    val isAdminCredentialsUseCase: IsAdminCredentialsUseCase,
    val setLoggedUseCase: SetLoggedUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(
            isAdminCredentialsUseCase,
            setLoggedUseCase
        ) as T
    }
}