package com.example.memory_app.presentation.menuscreen.viewmodel

import com.example.memory_app.domain.model.Difficulty

sealed class InfoState {
    object Loading : InfoState()
    data class Error(val throwable: Throwable) : InfoState()
    data class Success(val levelsInfo : List<Pair<String, Difficulty>>) : InfoState()
}