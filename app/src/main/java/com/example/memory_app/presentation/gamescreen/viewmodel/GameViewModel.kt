package com.example.memory_app.presentation.gamescreen.viewmodel

import androidx.lifecycle.*
import com.example.memory_app.domain.model.Card
import com.example.memory_app.domain.model.Reaction
import com.example.memory_app.domain.use_cases.LoadLevelUseCase
import com.example.memory_app.domain.use_cases.SaveGameResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor
    (savedStateHandle : SavedStateHandle,
     loadLevelUseCase : LoadLevelUseCase,
     private val saveGameResultUseCase: SaveGameResultUseCase) : ViewModel() {

    val levelName = savedStateHandle.get<String>("levelName")!!
    private val game = loadLevelUseCase(levelName)


    private val _cardsBoard = MutableStateFlow(game.getBoard().cards)
    val cardsBoard : StateFlow<List<Card>> = _cardsBoard

    private val _mismatchesLeft = MutableStateFlow(game.getBoard().mismatchesLeft)
    val mismatchesLeft : StateFlow<Int> = _mismatchesLeft

    private val overEventChannel = Channel<Over>()
    val overEvent = overEventChannel.receiveAsFlow()


    fun onCardClicked(position : Int) {
        val reaction = game.onCardSelected(position)
        when(reaction) {
            // Nothing reaction won`t return if View properly sets
            // click listeners and also handle Game Over
            is Reaction.Nothing -> {}

            is Reaction.Running -> {
                _cardsBoard.value = reaction.cards
                _mismatchesLeft.value = reaction.mismatchesLeft
            }

            is Reaction.Loss -> {
                _cardsBoard.value = reaction.cards
                _mismatchesLeft.value = 0
                viewModelScope.launch {
                    overEventChannel.send(Over.Failure)
                }
            }

            is Reaction.Finished -> {
                _cardsBoard.value = reaction.cards
                viewModelScope.launch {
                    val resultScore = saveGameResultUseCase(reaction.mismatchedTimes, levelName)
                    viewModelScope.launch {
                        overEventChannel.send(Over.Success(resultScore))
                    }
                }
            }
        }
    }
}