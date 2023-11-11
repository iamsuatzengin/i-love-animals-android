package com.suatzengin.iloveanimals.ui.advertisement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvertisementViewModel @Inject constructor(
    private val repository: AdvertisementRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdvertisementUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAdvertisementList()
    }

    fun updateSelectedCategory(category: AdvertisementCategory) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedCategory = category) }
        }
    }

    fun getAdvertisementList() {
        viewModelScope.launch {
            repository.getAdvertisementList().collect { result ->
                when (result) {
                    is Resource.Error -> {}

                    Resource.Loading -> {}

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                advertisementList = result.data.orEmpty(),
                                selectedCategory = AdvertisementCategory.ALL
                            )
                        }
                    }
                }
            }
        }
    }

    fun getAdvertisementsByCategory(category: AdvertisementCategory) {
        viewModelScope.launch {
            repository.getAdvertisementsByCategory(uiState.value.selectedCategory)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {}

                        Resource.Loading -> {}

                        is Resource.Success -> {

                            _uiState.update {
                                it.copy(
                                    advertisementList = result.data.orEmpty(),
                                    selectedCategory = category
                                )
                            }
                        }
                    }
                }
        }
    }
}
