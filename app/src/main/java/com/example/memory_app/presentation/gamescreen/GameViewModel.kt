package com.example.memory_app.presentation.gamescreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.memory_app.domain.use_cases.LoadGameUseCase
import com.example.memory_app.domain.use_cases.SaveGameResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor
    (private val loadGameUseCase : LoadGameUseCase,
     private val saveGameResultUseCase: SaveGameResultUseCase,
     savedStateHandle : SavedStateHandle) : ViewModel() {

    private val levelName = savedStateHandle.get<String>("levelName")
}