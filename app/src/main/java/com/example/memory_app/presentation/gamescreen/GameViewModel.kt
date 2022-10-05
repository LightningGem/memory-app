package com.example.memory_app.presentation.gamescreen

import androidx.lifecycle.*
import com.example.memory_app.domain.model.Card
import com.example.memory_app.domain.model.Reaction
import com.example.memory_app.domain.repository.Score
import com.example.memory_app.domain.use_cases.LoadLevelUseCase
import com.example.memory_app.domain.use_cases.SaveGameResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor
    (private val loadLevelUseCase : LoadLevelUseCase,
     private val saveGameResultUseCase: SaveGameResultUseCase,
     savedStateHandle : SavedStateHandle) : ViewModel() {

    private val levelName = savedStateHandle.get<String>("levelName")
    private val game = loadLevelUseCase(levelName!!)

    private val _cardsBoard = MutableLiveData(game.getBoard().cards)
    val cardsBoard : LiveData<List<Card>> = _cardsBoard

    private val _finalScore = MutableLiveData<Score>()
    val finalScore : LiveData<Score> = _finalScore


    fun onCardClicked(position : Int) {
        val reaction = game.onCardSelected(position)
        when(reaction) {
            is Reaction.Running -> {
                _cardsBoard.value = reaction.cards
            }
            is Reaction.OpenItemSelected -> {
                // Show message
            }
            is Reaction.Finished -> {
                _cardsBoard.value = reaction.cards
                viewModelScope.launch {
                    val resultScore = saveGameResultUseCase(reaction.mismatchedTimes, levelName!!)
                    _finalScore.value = resultScore
                }
            }
        }
    }
}