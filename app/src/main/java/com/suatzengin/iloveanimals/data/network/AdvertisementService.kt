package com.suatzengin.iloveanimals.data.network

import com.suatzengin.iloveanimals.data.model.MessageResponse
import com.suatzengin.iloveanimals.data.model.advertisement.AdvertisementApiModel
import com.suatzengin.iloveanimals.data.model.advertisement.CreateAdvertisementRequest
import com.suatzengin.iloveanimals.data.network.NetworkConstants.ADVERTISEMENT_LIST
import com.suatzengin.iloveanimals.data.network.NetworkConstants.CREATE_ADVERTISEMENT
import com.suatzengin.iloveanimals.data.network.NetworkConstants.QUERY_KEY
import com.suatzengin.iloveanimals.data.network.NetworkConstants.SEARCH_ADVERTISEMENT
import com.suatzengin.iloveanimals.data.network.NetworkConstants.USER_ADVERTISEMENT
import com.suatzengin.iloveanimals.domain.model.advertisement.AdvertisementCategory
import com.suatzengin.iloveanimals.util.extension.apiCall
import com.suatzengin.iloveanimals.util.extension.apiCallWithFlow
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import kotlinx.coroutines.flow.flow
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

    fun getAdvertisementsByCategory(category: AdvertisementCategory) =
        apiCallWithFlow<List<AdvertisementApiModel>> {
            client.get(ADVERTISEMENT_LIST) {
                url {
                    appendPathSegments("${AdvertisementCategory.getCategoryWithId(category)}")
                }
            }
        }

    fun getUserAdvertisement(userId: String) = flow<List<AdvertisementApiModel>> {
        val response = client.get(USER_ADVERTISEMENT) {
            url {
                appendPathSegments(userId)
            }
        }

        emit(response.body())
    }

    suspend fun createAdvertisement(requestBody: CreateAdvertisementRequest) =
        apiCall<MessageResponse> {
            client.post(CREATE_ADVERTISEMENT) {
                contentType(ContentType.Application.Json)

                setBody(requestBody)
            }
        }
}
