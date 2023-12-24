package com.suatzengin.iloveanimals.ui.charityscore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.data.auth.IlaAuthHandler
import com.suatzengin.iloveanimals.domain.model.onError
import com.suatzengin.iloveanimals.domain.model.onSuccess
import com.suatzengin.iloveanimals.domain.repository.CharityScoreRepository
import com.suatzengin.iloveanimals.util.jwtdecode.JwtDecoder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharityScoreViewModel @Inject constructor(
    private val repository: CharityScoreRepository,
    private val ilaAuthHandler: IlaAuthHandler
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharityScoreUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCharityScores()
    }

    private fun getCharityScores() {
        viewModelScope.launch {
            repository.getCharityScores().collect { result ->
                result.onSuccess { list ->
                    _uiState.update { it.copy(scores = list.orEmpty()) }
                    getCurrentUserScore()
                }.onError {
                    Log.i("Charity", "error message: $it")
                }
            }
        }
    }

    private fun getCurrentUserScore() {
        viewModelScope.launch {
            val userId = JwtDecoder.decode(ilaAuthHandler.accessToken).userId

            val userScore = uiState.value.scores.firstOrNull {
                it.userId == userId
            }

            _uiState.update { it.copy(currentUserScore = userScore) }
        }
    }
}
