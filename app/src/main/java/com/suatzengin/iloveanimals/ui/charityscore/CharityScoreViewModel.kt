package com.suatzengin.iloveanimals.ui.charityscore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suatzengin.iloveanimals.domain.model.charityscore.CharityScore
import com.suatzengin.iloveanimals.domain.model.onError
import com.suatzengin.iloveanimals.domain.model.onSuccess
import com.suatzengin.iloveanimals.domain.repository.CharityScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharityScoreViewModel @Inject constructor(
    private val repository: CharityScoreRepository
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
                }.onError {
                    Log.i("Charity", "error message: $it")
                }
            }
        }
    }
}

data class CharityScoreUiState(
    val scores: List<CharityScore> = emptyList()
)
