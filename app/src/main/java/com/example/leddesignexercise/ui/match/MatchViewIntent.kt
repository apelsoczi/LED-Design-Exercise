package com.example.leddesignexercise.ui.match

import com.example.leddesignexercise.data.model.Letter


/**
 *  Represents view intents created by either the system
 *  or user in [MatchFragment] handled by [MatchViewModel]
 */
sealed class MatchViewIntent {

    /* The onViewCreated() method was called */
    object ViewOnCreated : MatchViewIntent()

    /* The user pressed a button */
    data class ButtonPress(val letter: Letter) : MatchViewIntent()

    /* The user selected start a new match from the dialog prompt */
    object NewMatch : MatchViewIntent()

}