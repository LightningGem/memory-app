package com.example.memory_app.presentation.gamescreen.viewmodel

import com.example.memory_app.domain.repository.Score

sealed class Over {
    data class Success(val score : Score) : Over()
    object Failure : Over()
}