package com.example.leddesignexercise.data

import com.example.leddesignexercise.data.model.Letter


object MatchRepository {

    var match: Match? = null
        private set

    fun create() {
        val letters = listOf<Letter>(
            Letter.values().random(),
            Letter.values().random(),
            Letter.values().random()
        )
        match = Match(letters)
    }

    fun delete() {
        match = null
    }

}