package com.suatzengin.iloveanimals.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.onError
import com.suatzengin.iloveanimals.domain.model.onLoading
import com.suatzengin.iloveanimals.domain.model.onSuccess
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AdvertisementRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val key = savedStateHandle.get<String>(SEARCH_KEY)
        searchAdvertisement(key)
    }

    fun searchAdvertisement(key: String?) {
        viewModelScope.launch {
            key?.let { searchKey ->
                repository.searchAdvertisement(searchKey).collect { result ->
                    result.onSuccess { advertisements ->
                        _uiState.update { it.copy(list = advertisements.orEmpty()) }
                    }.onError { message ->
                        _uiState.update { it.copy(message = message) }
                    }.onLoading {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    companion object {
        private const val SEARCH_KEY = "key"
    }
}
