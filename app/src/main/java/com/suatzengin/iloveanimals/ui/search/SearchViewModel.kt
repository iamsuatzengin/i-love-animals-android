package com.suatzengin.iloveanimals.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.Advertisement
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
        val key = savedStateHandle.get<String>("key")
        searchAdvertisement(key)
    }

    private fun searchAdvertisement(key: String?) {
        viewModelScope.launch {
            key?.let { searchKey ->
                repository.searchAdvertisement(searchKey).collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _uiState.update { it.copy(message = result.message) }
                        }
                        Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                        is Resource.Success -> {
                            _uiState.update { it.copy(list = result.data.orEmpty()) }
                        }
                    }
                }
            }
        }
    }
}

data class SearchUiState(
    val list: List<Advertisement> = emptyList(),
    val isLoading: Boolean = false,
    val message: String = "",
)
