package com.example.shop.presentation.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<VS: ViewState> : ViewModel(), com.example.shop.presentation.arch.ViewModel<VS> {
    final override val viewStateLiveData: MutableLiveData<VS> by lazy {
        val liveData = OnActiveLiveData(initialState)
        return@lazy liveData
    }

    private inner class OnActiveLiveData<VS : ViewState>(private val initialState: VS) : MutableLiveData<VS>() {
        private var isActiveHasFired = false
        override fun onActive() {
            super.onActive()
            if (!isActiveHasFired) {
                isActiveHasFired = true
                postValue(initialState)
            }

        }
    }
}