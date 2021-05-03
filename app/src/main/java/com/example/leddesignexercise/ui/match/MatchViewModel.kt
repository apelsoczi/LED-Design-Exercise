package com.example.leddesignexercise.ui.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leddesignexercise.ui.match.MatchViewEvent.*
import com.example.leddesignexercise.ui.match.MatchViewIntent.*
import com.example.leddesignexercise.ui.match.MatchViewState.*
import com.example.leddesignexercise.data.model.Letter
import com.example.leddesignexercise.data.MatchRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class MatchViewModel(val repository: MatchRepository = MatchRepository) : ViewModel() {

    private val _state = MutableStateFlow<MatchViewState>(Initial)
    val state = _state

    private val _events = MutableSharedFlow<MatchViewEvent>()
    val events = _events

    /* handle actions and intents from [MatchFragment] */
    fun handle(intent: MatchViewIntent) {
        when (intent) {
            is ViewOnCreated -> loadMatch()
            is ButtonPress -> pressButton(intent.letter)
            is NewMatch -> newMatch()
        }
    }

    private fun loadMatch() {
        if (repository.match == null) {
            repository.create()
        }

        repository.match?.let {
            _state.value = if (it.buttons.isEmpty()) {
                Idle(it.sequence)
            } else {
                Attempt(
                    answer = it.sequence,
                    buttons = it.buttons,
                    firstColor = it.colors[0],
                    secondColor = it.colors[1],
                    thirdColor = it.colors[2]
                )
            }
        }
    }

    private fun pressButton(letter: Letter) = viewModelScope.launch {
        repository.match?.let {
            it.handleButtonPress(letter)

            _state.value = Attempt(
                answer = it.sequence,
                buttons = it.buttons,
                firstColor = it.colors[0],
                secondColor = it.colors[1],
                thirdColor = it.colors[2]
            )

            if (it.correct) {
                _events.emit(NewMatchDialog)
            }
        }
    }

    private fun newMatch() {
        repository.delete()
        loadMatch()
    }

}