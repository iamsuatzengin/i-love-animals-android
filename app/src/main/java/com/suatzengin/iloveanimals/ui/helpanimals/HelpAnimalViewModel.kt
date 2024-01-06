package com.suatzengin.iloveanimals.ui.helpanimals

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.suatzengin.iloveanimals.BuildConfig
import com.suatzengin.iloveanimals.data.model.advertisement.CompleteAdvertisementRequest
import com.suatzengin.iloveanimals.domain.model.Resource
import com.suatzengin.iloveanimals.domain.repository.CompleteAdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelpAnimalViewModel @Inject constructor(
    private val completeAdRepository: CompleteAdRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HelpAnimalUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HelpAnimalUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro-vision",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    fun updateImages(imageList: List<Uri>) {
        _uiState.update { it.copy(images = imageList) }
    }

    fun handleHelpApproval(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {

                val inputContent = content {
                    text("Can you describe this image in Turkish and in the end, answer as \"TRUE\" or \"FALSE\" if someone feed the animal or animals?")
                    image(bitmap)
                }

                val response = generativeModel.generateContent(inputContent)

                val isSuccess = response.text.orEmpty().contains("TRUE")

                _uiState.update { it.copy(responseText = response.text.orEmpty()) }

                if (isSuccess) _uiEvent.emit(HelpAnimalUiEvent.Approved())
                else _uiEvent.emit(HelpAnimalUiEvent.Denied())

            }.onFailure {
                Log.e("Gemini", "${it.message}")
                _uiEvent.emit(HelpAnimalUiEvent.Denied())
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun completeAdvertisement(
        advertisementId: String,
        advertisementCreatorId: String
    ) {
        viewModelScope.launch {
            val requestBody = CompleteAdvertisementRequest(
                advertisementId = advertisementId,
                creatorId = advertisementCreatorId
            )

            when (
                val response = completeAdRepository.completeAdvertisement(requestBody = requestBody)
            ) {
                is Resource.Error -> {
                    Log.e("Complete", "error: ${response.message}")
                }

                Resource.Loading -> Unit

                is Resource.Success -> {
                    _uiEvent.emit(HelpAnimalUiEvent.CompletedAndNavigateAdList(response.data?.message.orEmpty()))
                }
            }
        }
    }
}
