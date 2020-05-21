/*
 * BaseViewModel.kt
 * Created by Sanjay.Sah
 */

package com.sanjay.gutenberg.ui

import androidx.lifecycle.ViewModel
import com.sanjay.gutenberg.constants.State
import com.sanjay.gutenberg.utils.SingleLiveEvent

import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    var status = SingleLiveEvent<State>()

    var disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}