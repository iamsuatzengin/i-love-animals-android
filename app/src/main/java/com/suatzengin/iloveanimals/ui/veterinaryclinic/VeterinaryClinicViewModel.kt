package com.suatzengin.iloveanimals.ui.veterinaryclinic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.veterinaryclinic.VeterinaryClinic
import com.suatzengin.iloveanimals.domain.repository.VeterinaryClinicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VeterinaryClinicViewModel @Inject constructor(
    private val repository: VeterinaryClinicRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VeterinaryClinicUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getNearbyVeterinaryClinics("45400")
    }

    fun getNearbyVeterinaryClinics(postalCode: String) {
        viewModelScope.launch {
            repository.getNearbyVeterinaryClinics(postalCode).collect { result ->
                when (result) {
                    Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                clinics = result.data.orEmpty(),
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        // no-op for now
                    }
                }
            }
        }
    }
}

data class VeterinaryClinicUiState(
    val clinics: List<VeterinaryClinic> = emptyList(),
    val isLoading: Boolean = false,
)