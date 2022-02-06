package com.example.shop.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.usecase.DeleteItemUseCase
import com.example.shop.domain.usecase.GetItemByIdUseCase
import com.example.shop.domain.usecase.UpdateItemUseCase
import com.example.shop.presentation.item.edit.EditItemViewModel

class EditItemViewModelFactory(
    val updateItemUseCase: UpdateItemUseCase,
    val getItemByIdUseCase: GetItemByIdUseCase,
    val deleteItemUseCase: DeleteItemUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditItemViewModel(
            updateItemUseCase,
            getItemByIdUseCase,
            deleteItemUseCase) as T
    }
}