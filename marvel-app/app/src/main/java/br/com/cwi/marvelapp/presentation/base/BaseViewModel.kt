package br.com.cwi.marvelapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _emptyData = MutableLiveData<Boolean>()
    val emptyData: LiveData<Boolean> = _emptyData

    fun launch(disabledLoading: Boolean = false, block: suspend () -> Unit) {
        if (disabledLoading.not())  _loading.postValue(true)

        viewModelScope.launch {
            try {
                block()
            } catch (ex: Exception) {
                _error.postValue(true)
            }

        }
    }
}