package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.model.veterinaryclinic.VeterinaryClinicApiModel
import com.suatzengin.iloveanimals.data.network.NetworkConstants.VETERINARY_CLINICS
import com.suatzengin.iloveanimals.util.extension.apiCallWithFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import javax.inject.Inject

class VeterinaryClinicService @Inject constructor(
    private val client: HttpClient,
) {

    fun getNearbyVeterinaryClinics(postalCode: String) =
        apiCallWithFlow<List<VeterinaryClinicApiModel>> {
            client.get(VETERINARY_CLINICS) {
                url {
                    appendPathSegments(postalCode)
                }
            }
        }
}
