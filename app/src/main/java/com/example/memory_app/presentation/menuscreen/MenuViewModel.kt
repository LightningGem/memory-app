package com.example.memory_app.presentation.menuscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.memory_app.domain.use_cases.LoadLevelsInfoUseCase
import com.example.memory_app.domain.use_cases.LoadStatisticUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor
    (loadStatisticUseCase: LoadStatisticUseCase,
     loadLevelsInfoUseCase: LoadLevelsInfoUseCase) : ViewModel() {

    val statistic = loadStatisticUseCase().asLiveData()
    val levels = loadLevelsInfoUseCase().asLiveData()
}