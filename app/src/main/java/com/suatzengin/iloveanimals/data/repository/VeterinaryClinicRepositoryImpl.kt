package com.suatzengin.iloveanimals.data.repository

import com.suatzengin.iloveanimals.data.network.VeterinaryClinicService
import com.suatzengin.iloveanimals.domain.mapper.VeterinaryClinicMapper
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.model.veterinaryclinic.VeterinaryClinic
import com.suatzengin.iloveanimals.domain.repository.VeterinaryClinicRepository
import com.suatzengin.iloveanimals.util.extension.mapOnSuccess
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VeterinaryClinicRepositoryImpl @Inject constructor(
    private val service: VeterinaryClinicService,
    private val mapper: VeterinaryClinicMapper
) : VeterinaryClinicRepository {
    override fun getNearbyVeterinaryClinics(postalCode: String): Flow<Resource<List<VeterinaryClinic>>> {
        return service.getNearbyVeterinaryClinics(postalCode).mapOnSuccess { clinics ->
            mapper.map(clinics)
        }
    }
}
