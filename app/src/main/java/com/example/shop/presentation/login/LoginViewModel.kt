package com.example.shop.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.model.Item
import com.example.shop.domain.usecase.GetItemByIdUseCase
import com.example.shop.domain.usecase.IsAdminCredentialsUseCase
import com.example.shop.domain.usecase.SetLoggedUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.math.log

sealed class LoginViewState {
    object WrongCredentials : LoginViewState()
    object LoggedAsAdmin : LoginViewState()
    class Error(val message: String) : LoginViewState()
}

class LoginViewModel(
    private val isAdminCredentialsUseCase: IsAdminCredentialsUseCase,
    private val setLoggedUseCase: SetLoggedUseCase
) : ViewModel() {

    private val viewStateLiveData: MutableLiveData<LoginViewState> = MutableLiveData()

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