package com.example.shop.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.usecase.GetItemByIdUseCase
import com.example.shop.presentation.item.view.ViewItemViewModel

class ViewItemViewModelFactory(
    val getItemByIdUseCase: GetItemByIdUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewItemViewModel(
            getItemByIdUseCase
        ) as T
    }
}