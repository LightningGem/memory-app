package com.example.memory_app.presentation.gamescreen.viewmodel

import com.example.memory_app.domain.entities.Score

sealed class Over {
    data class Success(val score : Score) : Over()
    object Failure : Over()
}