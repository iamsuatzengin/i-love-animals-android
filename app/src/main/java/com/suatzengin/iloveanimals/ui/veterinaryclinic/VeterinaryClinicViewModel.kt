package com.suatzengin.iloveanimals.ui.veterinaryclinic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.data.local.LocalDataStoreManager
import com.suatzengin.iloveanimals.domain.model.onLoading
import com.suatzengin.iloveanimals.domain.model.onSuccess
import com.suatzengin.iloveanimals.domain.repository.VeterinaryClinicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VeterinaryClinicViewModel @Inject constructor(
    private val repository: VeterinaryClinicRepository,
    private val localDataStoreManager: LocalDataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(VeterinaryClinicUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getNearbyVeterinaryClinics()
    }

    private fun getNearbyVeterinaryClinics() {
        viewModelScope.launch {
            val postalCode = localDataStoreManager.postalCode.first()

            repository.getNearbyVeterinaryClinics(postalCode).collect { result ->
                result.onLoading {
                    _uiState.update { it.copy(isLoading = true) }
                }.onSuccess { list ->
                    _uiState.update {
                        it.copy(clinics = list.orEmpty(), isLoading = false)
                    }
                }
            }
        }
    }
}
