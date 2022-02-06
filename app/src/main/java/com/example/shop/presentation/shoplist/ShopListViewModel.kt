package com.example.shop.presentation.shoplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.model.Item
import com.example.shop.domain.repository.LoginRepository
import com.example.shop.domain.usecase.GetListUseCase
import com.example.shop.domain.usecase.IsLoggedUseCase
import com.example.shop.domain.usecase.SetLoggedUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class ShopListViewState {
    object Loading : ShopListViewState()
    class ItemsLoaded(val items: List<Item>) : ShopListViewState()
    class Error(val message: String) : ShopListViewState()
}

class ShopListViewModel(
    private val getListUseCase: GetListUseCase,
    private val isLoggedUseCase: IsLoggedUseCase,
    private val setLoggedUseCase: SetLoggedUseCase
    ) : ViewModel() {

    private val viewStateLiveData: MutableLiveData<ShopListViewState> = MutableLiveData()

    val viewState: LiveData<ShopListViewState>
        get() = viewStateLiveData

    fun getItems() {
        viewModelScope.launch {
            getListUseCase.invoke()
                .onStart {
                    viewStateLiveData.postValue(ShopListViewState.Loading)
                }
                .catch { result ->
                    viewStateLiveData.postValue(ShopListViewState.Error(result.message!!))
                }
                .collect {
                    viewStateLiveData.postValue(ShopListViewState.ItemsLoaded(it))
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
                    viewStateLiveData.postValue(ShopListViewState.Loading)
                }
                .catch { result ->
                    viewStateLiveData.postValue(ShopListViewState.Error(result.message!!))
                }
                .collect {
                    viewStateLiveData.postValue(ShopListViewState.ItemsLoaded(it))
                }
            .launchIn(viewModelScope)
         */
        }
    }

    fun isUserLogged(): Boolean {
        return isLoggedUseCase.invoke()
    }

    fun setUserLoggedValue(isLogged: Boolean) {
        setLoggedUseCase.invoke(isLogged)
    }
}