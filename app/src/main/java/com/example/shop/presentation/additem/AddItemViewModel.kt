package com.example.shop.presentation.additem

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.model.Item
import com.example.shop.domain.usecase.CreateItemUseCase
import com.example.shop.presentation.arch.BaseViewModel
import com.example.shop.presentation.arch.ViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

sealed class AddItemViewState : ViewState() {
    object OK : AddItemViewState()
    object Loading : AddItemViewState()
    object ItemAdded : AddItemViewState()
    object EmptyFields : AddItemViewState()
    class Error(val message: String) : AddItemViewState()
}

class AddItemViewModel(
    private val createItemUseCase: CreateItemUseCase
) : BaseViewModel<AddItemViewState>() {
    override val initialState: AddItemViewState
        get() = AddItemViewState.Loading

    val viewState: LiveData<AddItemViewState>
        get() = viewStateLiveData

    fun createItem(name: String,
                   description: String,
                   price: String,
                   imageUrl: String) {
        var hasErrors = false
        if (name.isBlank() || description.isBlank() || price.isBlank() || imageUrl.isBlank()) {
            hasErrors = true
            viewStateLiveData.postValue(AddItemViewState.EmptyFields)
        }
        if (!hasErrors) {
            val item = Item(UUID.randomUUID().toString(), name, description, price, imageUrl)
            viewModelScope.launch {
                createItemUseCase.invoke(item)
                    .onStart {
                        viewStateLiveData.postValue(AddItemViewState.Loading)
                    }
                    .catch { result ->
                        viewStateLiveData.postValue(AddItemViewState.Error(result.message!!))
                    }
                    .onCompletion {
                        viewStateLiveData.postValue(AddItemViewState.ItemAdded)
                    }
                    .collect()
            }
        }
    }
}