package com.suatzengin.iloveanimals.domain.mapper

import com.suatzengin.iloveanimals.data.model.veterinaryclinic.VeterinaryClinicApiModel
import com.suatzengin.iloveanimals.domain.model.veterinaryclinic.Address
import com.suatzengin.iloveanimals.domain.model.veterinaryclinic.VeterinaryClinic
import javax.inject.Inject

class VeterinaryClinicMapper @Inject constructor() :
    Mapper<VeterinaryClinicApiModel, VeterinaryClinic> {
    override fun map(input: VeterinaryClinicApiModel): VeterinaryClinic {
        return VeterinaryClinic(
            id = input.id,
            clinicName = input.clinicName,
            doctorName = input.doctorName,
            openTimes = input.openTimes,
            closeTimes = input.closeTimes,
            isAmbulanceAvailable = input.isAmbulanceAvailable,
            images = input.images,
            address = Address(
                latitude = input.latitude,
                longitude = input.longitude,
                address = input.address,
                postalCode = input.postalCode
            ),
            phoneNumber = input.phoneNumber
        )
    }

    fun map(input: List<VeterinaryClinicApiModel>) = input.map { map(it) }
}
