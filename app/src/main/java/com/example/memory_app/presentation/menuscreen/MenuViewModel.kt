package com.example.memory_app.presentation.menuscreen

import androidx.lifecycle.ViewModel
import com.example.memory_app.domain.use_cases.LoadLevelsInfoUseCase
import com.example.memory_app.domain.use_cases.LoadStatisticUseCase

class MenuViewModel(private val loadStatisticUseCase: LoadStatisticUseCase,
                    private val loadLevelsInfoUseCase: LoadLevelsInfoUseCase) : ViewModel()