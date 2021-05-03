package com.example.leddesignexercise.ui.match

import com.example.leddesignexercise.data.model.Color
import com.example.leddesignexercise.data.model.Letter


/**
 *  Represents view states for [MatchFragment] to render
 */
sealed class MatchViewState {

    /* The UI is initializing */
    object Initial : MatchViewState()

    /* Displays the match sequence letters */
    data class Idle(val sequence: List<Letter>) : MatchViewState()

    /* Displays the match sequence letters and renders the users button press attempts */
    data class Attempt(
        val answer: List<Letter>,
        val buttons: List<Letter>,
        val firstColor: Color,
        val secondColor: Color,
        val thirdColor: Color
    ) : MatchViewState()

}