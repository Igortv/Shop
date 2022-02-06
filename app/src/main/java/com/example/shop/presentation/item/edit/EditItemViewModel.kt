package com.example.shop.presentation.item.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.model.Item
import com.example.shop.domain.usecase.DeleteItemUseCase
import com.example.shop.domain.usecase.GetItemByIdUseCase
import com.example.shop.domain.usecase.UpdateItemUseCase
import com.example.shop.presentation.additem.AddItemViewState
import com.example.shop.presentation.item.view.ViewItemViewState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed class EditItemViewState {
    object Loading : EditItemViewState()
    class ItemLoaded(val item: Item) : EditItemViewState()
    object ItemUpdated : EditItemViewState()
    object ItemDeleted: EditItemViewState()
    object EmptyFields : EditItemViewState()
    class Error(val message: String) : EditItemViewState()
}

class EditItemViewModel(
    private val updateItemUseCase: UpdateItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModel() {
    private val viewStateLiveData: MutableLiveData<EditItemViewState> = MutableLiveData()

    val viewState: LiveData<EditItemViewState>
        get() = viewStateLiveData

    fun getItem(itemId: String) {
        viewModelScope.launch {
            getItemByIdUseCase.invoke(itemId)
                .onStart {
                    viewStateLiveData.postValue(EditItemViewState.Loading)
                }
                .catch { result ->
                    viewStateLiveData.postValue(EditItemViewState.Error(result.message!!))
                }
                .collect {
                    viewStateLiveData.postValue(EditItemViewState.ItemLoaded(it))
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

    fun updateItem(id: String,
                   name: String,
                   description: String,
                   price: String,
                   imageUrl: String) {

        var hasErrors = false
        if (name.isBlank() || description.isBlank() || price.isBlank() || imageUrl.isBlank()) {
            hasErrors = true
            viewStateLiveData.postValue(EditItemViewState.EmptyFields)
        }
        if (!hasErrors) {
            val item = Item(id, name, description, price, imageUrl)
            viewModelScope.launch {
                updateItemUseCase.invoke(item)
                    .onStart {
                        viewStateLiveData.postValue(EditItemViewState.Loading)
                    }
                    .catch { result ->
                        viewStateLiveData.postValue(EditItemViewState.Error(result.message!!))
                    }
                    .onCompletion {
                        viewStateLiveData.postValue(EditItemViewState.ItemUpdated)
                    }
                    .collect()
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
                    viewStateLiveData.postValue(EditItemViewState.Loading)
                }
                .catch { result ->
                    viewStateLiveData.postValue(EditItemViewState.Error(result.message!!))
                }
                .collect {
                    viewStateLiveData.postValue(EditItemViewState.ItemUpdated)
                }
                .launchIn(viewModelScope)
             */
            }
        }
    }

    fun deleteItem(itemId: String) {
        viewModelScope.launch {
            deleteItemUseCase.invoke(itemId)
                .onStart {
                    viewStateLiveData.postValue(EditItemViewState.Loading)
                }
                .catch { result ->
                    viewStateLiveData.postValue(EditItemViewState.Error(result.message!!))
                }
                .onCompletion {
                    viewStateLiveData.postValue(EditItemViewState.ItemDeleted)
                }
                .collect()
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