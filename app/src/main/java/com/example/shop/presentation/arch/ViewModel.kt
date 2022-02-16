package com.example.shop.presentation.arch

import androidx.lifecycle.MutableLiveData

interface ViewModel<VS: ViewState> {
    val initialState: VS
    val viewStateLiveData: MutableLiveData<VS>
}