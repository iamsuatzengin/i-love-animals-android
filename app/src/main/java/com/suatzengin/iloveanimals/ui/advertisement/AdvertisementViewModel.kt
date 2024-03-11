package com.suatzengin.iloveanimals.ui.advertisement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.domain.model.onError
import com.suatzengin.iloveanimals.domain.model.onLoading
import com.suatzengin.iloveanimals.domain.model.onSuccess
import com.suatzengin.iloveanimals.domain.repository.AdvertisementRepository
import com.suatzengin.iloveanimals.domain.usecase.GetAdvertisementListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvertisementViewModel @Inject constructor(
    private val repository: AdvertisementRepository,
    private val getAdvertisementListUseCase: GetAdvertisementListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdvertisementUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AdvertisementUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun updateSelectedCategory(category: AdvertisementCategory) {
        viewModelScope.launch {
            _uiState.update { it.copy(selectedCategory = category) }
        }
    }

    private fun getAdvertisementList(
        newPostalCode: String? = null,
        canUpdate: Boolean = false,
        isRefreshing: Boolean = false,
    ) {
        viewModelScope.launch {
            getAdvertisementListUseCase(
                newPostalCode = newPostalCode,
                canUpdate = canUpdate
            ).collect { result ->
                result.onLoading {
                    _uiState.update { it.copy(isRefreshing = isRefreshing) }
                }.onError {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEvent.emit(AdvertisementUiEvent.ShowErrorMessage)
                }.onSuccess { advertisementList ->
                    _uiState.update {
                        it.copy(
                            advertisementList = advertisementList.orEmpty(),
                            selectedCategory = AdvertisementCategory.ALL,
                            emptyState = advertisementList.isNullOrEmpty(),
                            isRefreshing = false,
                            isLoading = false
                        )
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
        getAdvertisementList(newPostalCode = postalCode, canUpdate = true)
    }

    fun getAdvertisementsAllOrOnlyNearby(isRefreshing: Boolean = false) {
        getAdvertisementList(isRefreshing = isRefreshing)
    }

    fun updateIsLoading(isLoading: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = isLoading) }
        }
    }
}
