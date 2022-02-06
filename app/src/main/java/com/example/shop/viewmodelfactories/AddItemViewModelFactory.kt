package com.example.shop.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.usecase.CreateItemUseCase
import com.example.shop.presentation.additem.AddItemViewModel

class AddItemViewModelFactory(
    val createItemUseCase: CreateItemUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddItemViewModel(createItemUseCase) as T
    }
}