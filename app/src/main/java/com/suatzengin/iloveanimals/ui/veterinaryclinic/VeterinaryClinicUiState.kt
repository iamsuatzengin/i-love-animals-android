package com.suatzengin.iloveanimals.ui.veterinaryclinic

import com.suatzengin.iloveanimals.domain.model.veterinaryclinic.VeterinaryClinic

data class VeterinaryClinicUiState(
    val clinics: List<VeterinaryClinic> = emptyList(),
    val isLoading: Boolean = false,
)
