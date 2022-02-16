package com.example.shop.presentation.login

import androidx.lifecycle.LiveData
import com.example.shop.domain.usecase.IsAdminCredentialsUseCase
import com.example.shop.domain.usecase.SetLoggedUseCase
import com.example.shop.presentation.arch.BaseViewModel
import com.example.shop.presentation.arch.ViewState

sealed class LoginViewState : ViewState() {
    object OK : LoginViewState()
    object WrongCredentials : LoginViewState()
    object LoggedAsAdmin : LoginViewState()
    class Error(val message: String) : LoginViewState()
}

class LoginViewModel(
    private val isAdminCredentialsUseCase: IsAdminCredentialsUseCase,
    private val setLoggedUseCase: SetLoggedUseCase
) : BaseViewModel<LoginViewState>() {

    override val initialState: LoginViewState
        get() = LoginViewState.OK

    val viewState: LiveData<LoginViewState>
        get() = viewStateLiveData

    fun login(login: String, password: String) {
        val isAdminCredentials = isAdminCredentialsUseCase.invoke(login, password)
        if (isAdminCredentials) {
            setLoggedUseCase.invoke(isLogged = true)
            viewStateLiveData.postValue(LoginViewState.LoggedAsAdmin)
        } else {
            setLoggedUseCase.invoke(isLogged = false)
            viewStateLiveData.postValue(LoginViewState.WrongCredentials)
        }
    }
}