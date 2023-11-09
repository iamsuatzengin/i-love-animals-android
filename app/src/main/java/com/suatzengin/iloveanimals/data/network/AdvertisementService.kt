package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.model.advertisement.AdvertisementApiModel
import com.suatzengin.iloveanimals.data.network.NetworkConstants.ADVERTISEMENT_LIST
import com.suatzengin.iloveanimals.data.network.NetworkConstants.QUERY_KEY
import com.suatzengin.iloveanimals.data.network.NetworkConstants.SEARCH_ADVERTISEMENT
import com.suatzengin.iloveanimals.util.extension.apiCallWithFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class AdvertisementService @Inject constructor(
    private val client: HttpClient
) {
    fun getAdvertisementList() = apiCallWithFlow<List<AdvertisementApiModel>> {
        client.get(ADVERTISEMENT_LIST)
    }

    fun searchAdvertisement(key: String) =
        apiCallWithFlow<List<AdvertisementApiModel>> {
            client.get(SEARCH_ADVERTISEMENT) {
                url {
                    parameters.append(QUERY_KEY, key)
                }
            }
        }
}

