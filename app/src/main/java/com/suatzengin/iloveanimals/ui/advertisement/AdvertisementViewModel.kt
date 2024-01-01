package com.suatzengin.iloveanimals.ui.advertisement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.data.local.LocalDataStoreManager
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.domain.model.onSuccess
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvertisementViewModel @Inject constructor(
    private val repository: AdvertisementRepository,
    private val localDataStoreManager: LocalDataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdvertisementUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPostalCodeFromLocal()
    }

    private fun getPostalCodeFromLocal() {
        viewModelScope.launch {
            localDataStoreManager.postalCode.collectLatest { postalCode ->
                _uiState.update { it.copy(postalCode = postalCode) }
            }
        }
    }

    fun updateSelectedCategory(category: AdvertisementCategory) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedCategory = category) }
        }
    }

    private fun getAdvertisementList(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            repository.getAdvertisementList().collect { result ->
                when (result) {
                    is Resource.Error -> Unit

                    Resource.Loading -> {
                        _uiState.update { it.copy(isRefreshing = isRefreshing) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                advertisementList = result.data.orEmpty(),
                                selectedCategory = AdvertisementCategory.ALL,
                                isRefreshing = false,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getAdvertisementByPostalCode(postalCode: String, isRefreshing: Boolean = false) {
        viewModelScope.launch {
            repository.getAdvertisementsByPostalCode(postalCode).collect { result ->
                when (result) {
                    is Resource.Error -> Unit

                    Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true, isRefreshing = isRefreshing) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                advertisementList = result.data.orEmpty(),
                                isLoading = false,
                                isRefreshing = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun getAdvertisementsByCategory(category: AdvertisementCategory) {
        viewModelScope.launch {
            repository.getAdvertisementsByCategory(
                uiState.value.selectedCategory,
                uiState.value.postalCode
            ).collect { result ->
                result.onSuccess { advertisementList ->
                    _uiState.update {
                        it.copy(
                            advertisementList = advertisementList.orEmpty(),
                            selectedCategory = category
                        )
                    }
                }
            }
        }
    }

    fun updatePostalCode(postalCode: String?) {
        viewModelScope.launch {
            _uiState.update { it.copy(postalCode = postalCode.orEmpty()) }

            if (!postalCode.isNullOrEmpty()) {
                localDataStoreManager.savePostalCode(postalCode)
                getAdvertisementByPostalCode(postalCode)
            }
        }
    }

    fun getAdvertisementsAllOrOnlyNearby(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            if (uiState.value.postalCode.isEmpty()) {
                getAdvertisementList(isRefreshing)
                return@launch
            }

            getAdvertisementByPostalCode(uiState.value.postalCode, isRefreshing)
        }
    }

    fun updateIsLoading(isLoading: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = isLoading) }
        }
    }
}
