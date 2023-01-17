package com.example.memory_app.presentation.menuscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memory_app.domain.entities.Statistic
import com.example.memory_app.domain.model.Source
import com.example.memory_app.domain.use_cases.LoadLevelsInfoUseCase
import com.example.memory_app.domain.use_cases.LoadStatisticUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor
    (loadStatisticUseCase: LoadStatisticUseCase,
     private val loadLevelsInfoUseCase: LoadLevelsInfoUseCase) : ViewModel() {

    private val _levelsInfoState : MutableStateFlow<InfoState> = MutableStateFlow(InfoState.Loading)
    val levelsInfoState : StateFlow<InfoState> = _levelsInfoState

    private val _source = MutableStateFlow(Source.LOCAL)
    val source : StateFlow<Source> = _source

    val statistic: StateFlow<Statistic?> = loadStatisticUseCase().stateIn (
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = null
    )

    private fun getLevelsStream() = viewModelScope.launch {
        if(source.value == Source.REMOTE) _levelsInfoState.value = InfoState.Loading
        loadLevelsInfoUseCase(source = source.value)
                .catch { _levelsInfoState.value = InfoState.Error(it) }
                .collect { _levelsInfoState.value = InfoState.Success(it) }
    }

    private var stream = getLevelsStream()

    fun changeLevelsSource() {
        if (_source.value == Source.REMOTE) _source.value = Source.LOCAL
        else {_source.value = Source.REMOTE}
        retry()
    }

    fun retry() { stream.cancel().also { stream = getLevelsStream() } }
}