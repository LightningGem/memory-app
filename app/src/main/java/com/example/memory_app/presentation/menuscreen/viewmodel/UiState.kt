package com.example.memory_app.presentation.menuscreen.viewmodel

import com.example.memory_app.domain.model.Difficulty

sealed class UiState {
    object Loading : UiState()
    data class Error(val throwable: Throwable) : UiState()
    data class Success(val levelsInfo : List<Pair<String, Difficulty>>) : UiState()
}