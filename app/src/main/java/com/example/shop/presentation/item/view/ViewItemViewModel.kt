package com.example.shop.presentation.item.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.model.Item
import com.example.shop.domain.usecase.DeleteItemUseCase
import com.example.shop.domain.usecase.GetItemByIdUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class ViewItemViewState {
    object Loading : ViewItemViewState()
    class ItemLoaded(val item: Item) : ViewItemViewState()
    class Error(val message: String) : ViewItemViewState()
}

class ViewItemViewModel(
    private val getItemByIdUseCase: GetItemByIdUseCase
    ) : ViewModel() {

    private val viewStateLiveData: MutableLiveData<ViewItemViewState> = MutableLiveData()

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
            /*
            getListUseCase()
                .onStart {
                    viewStateLiveData.postValue(ShopListViewState.Loading)
                }
                .onEach { result ->
                    viewStateLiveData.postValue(ShopListViewState.ItemsLoaded(result))
                }
                .catch { result ->
                    viewStateLiveData.postValue(ShopListViewState.Error(result.message!!))
                }
                .onStart {
                    viewStateLiveData.postValue(ViewItemViewState.Loading)
                }
                .catch { result ->
                    viewStateLiveData.postValue(ViewItemViewState.Error(result.message!!))
                }
                .collect {
                    viewStateLiveData.postValue(ViewItemViewState.ItemLoaded(it))
                }
                .launchIn(viewModelScope)
             */
        }
    }
}