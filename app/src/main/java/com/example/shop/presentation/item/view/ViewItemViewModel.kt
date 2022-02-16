package com.example.shop.presentation.item.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.model.Item
import com.example.shop.domain.usecase.GetItemByIdUseCase
import com.example.shop.presentation.arch.BaseViewModel
import com.example.shop.presentation.arch.ViewState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class ViewItemViewState : ViewState() {
    object Loading : ViewItemViewState()
    class ItemLoaded(val item: Item) : ViewItemViewState()
    class Error(val message: String) : ViewItemViewState()
}

class ViewItemViewModel(
    private val getItemByIdUseCase: GetItemByIdUseCase
    ) : BaseViewModel<ViewItemViewState>() {

    override val initialState: ViewItemViewState
        get() = ViewItemViewState.Loading

    val viewState: LiveData<ViewItemViewState>
        get() = viewStateLiveData

    fun getItem(itemId: String) {
        viewModelScope.launch {
            getItemByIdUseCase.invoke(itemId)
                .onStart {
                    viewStateLiveData.postValue(ViewItemViewState.Loading)
                }
                .catch { result ->
                    viewStateLiveData.postValue(ViewItemViewState.Error(result.message!!))
                }
                .collect {
                    viewStateLiveData.postValue(ViewItemViewState.ItemLoaded(it))
                }
        }
    }
}