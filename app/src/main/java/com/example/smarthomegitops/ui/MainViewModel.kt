package com.example.smarthomegitops.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthomegitops.data.GitHubRepository
import com.example.smarthomegitops.domain.DeceptionDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface UiState {
    object Normal : UiState
    data class SecurityAlert(val confidenceScore: Int, val rawText: String) : UiState
}

class MainViewModel (
    private val repository: GitHubRepository = GitHubRepository(),
    private val detector: DeceptionDetector = DeceptionDetector()
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Normal)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        startPollingLoop()
    }

    private fun startPollingLoop() {
        viewModelScope.launch (Dispatchers.IO) {
            while (true) {
                try {
                    val latestComment = repository.fetchLatestAttackComment()

                    val detectionResult = detector.analyseComment(latestComment)

                    if (detectionResult.isAttack) {
                        _uiState.value = UiState.SecurityAlert(
                            confidenceScore = detectionResult.confidenceScore,
                            rawText = detectionResult.matchedText
                        )
                    } else {
                        _uiState.value = UiState.Normal
                    }
                } catch (e: Exception) {
                    Log.e("PollingError", "Error fetching comment", e)
                }

                delay(30_000L)
            }
        }
    }
//LAB 2
    /*
    fun forceRejectIncident() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = repository.forceRejectPullRequest(pullNumber = 5)

                if (success) {
                    _uiState.value = UiState.Normal
                }
            } catch (e: Exception ) {
                Log.e("GitOpsError", "Failed to force reject: ${e.localizedMessage}")
            }
        }
    }

    fun forceMergeProposal() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = repository.forceMergeProposal(pullNumber = 5)

                if (success) {
                    _uiState.value = UiState.Normal
                }
            } catch (e: Exception ) {
                Log.e("GitOpsError", "Failed to force reject: ${e.localizedMessage}")
            }
        }
    }

     */
}