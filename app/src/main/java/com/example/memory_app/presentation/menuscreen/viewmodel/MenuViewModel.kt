package com.example.memory_app.presentation.menuscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memory_app.domain.model.Difficulty
import com.example.memory_app.domain.repository.Statistic
import com.example.memory_app.domain.use_cases.LoadLevelsInfoUseCase
import com.example.memory_app.domain.use_cases.LoadStatisticUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor
    (loadStatisticUseCase: LoadStatisticUseCase,
     loadLevelsInfoUseCase: LoadLevelsInfoUseCase) : ViewModel() {
    val levelsInfo : StateFlow<List<Pair<String, Difficulty>>?> = loadLevelsInfoUseCase().stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = null
    )

    val statistic: StateFlow<Statistic?> = loadStatisticUseCase().stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = null
    )
}