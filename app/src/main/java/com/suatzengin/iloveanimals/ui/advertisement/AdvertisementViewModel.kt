package com.suatzengin.iloveanimals.ui.advertisement

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvertisementViewModel @Inject constructor(
    private val repository: AdvertisementRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<Advertisement>>(emptyList())
    val uiState = _uiState.asStateFlow()

    init {
        getAdvertisementList()
    }

    private fun getAdvertisementList() {
        viewModelScope.launch {
            repository.getAdvertisementList().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        Log.i("network", "Error: ${result.message}")
                    }
                    Resource.Loading -> {
                        Log.i("network", "Loading..")
                    }
                    is Resource.Success -> {
                        Log.i("network", "Data: ${result.data}")
                        _uiState.emit(result.data.orEmpty())
                    }
                }
            }
        }
    }
}
