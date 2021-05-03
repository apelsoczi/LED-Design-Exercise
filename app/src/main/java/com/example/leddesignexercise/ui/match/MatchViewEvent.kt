package com.example.leddesignexercise.ui.match


/**
 *  Represents view events sent to [MatchFragment] by [MatchViewModel]
 */
sealed class MatchViewEvent {

    /* Show the new match dialog prompt */
    object NewMatchDialog : MatchViewEvent()

}