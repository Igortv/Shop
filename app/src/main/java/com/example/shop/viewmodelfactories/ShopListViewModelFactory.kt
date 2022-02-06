package com.example.shop.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.repository.LoginRepository
import com.example.shop.domain.usecase.GetListUseCase
import com.example.shop.domain.usecase.IsLoggedUseCase
import com.example.shop.domain.usecase.SetLoggedUseCase
import com.example.shop.presentation.shoplist.ShopListViewModel

class ShopListViewModelFactory(
    val getListUseCase: GetListUseCase,
    val isLoggedUseCase: IsLoggedUseCase,
    val setLoggedUseCase: SetLoggedUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShopListViewModel(
            getListUseCase,
            isLoggedUseCase,
            setLoggedUseCase) as T
    }
}