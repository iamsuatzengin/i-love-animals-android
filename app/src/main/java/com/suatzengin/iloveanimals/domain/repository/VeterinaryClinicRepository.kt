package com.suatzengin.iloveanimals.domain.repository

import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.veterinaryclinic.VeterinaryClinic
import kotlinx.coroutines.flow.Flow

interface VeterinaryClinicRepository {
    fun getNearbyVeterinaryClinics(postalCode: String): Flow<Resource<List<VeterinaryClinic>>>
}
