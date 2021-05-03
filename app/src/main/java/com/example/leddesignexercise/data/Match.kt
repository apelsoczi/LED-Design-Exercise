package com.example.leddesignexercise.data

import com.example.leddesignexercise.data.model.Color
import com.example.leddesignexercise.data.model.Color.*
import com.example.leddesignexercise.data.model.Letter


class Match(val sequence: List<Letter>) {

    val buttons = mutableListOf<Letter>()

    var colors: MutableList<Color> = setColors()
        private set(value) {
            field = value
            correct = value.all { it == GREEN }
        }

    var correct = false

    /* Handle user button press input as a list of three most recent items and update colors list */
    fun handleButtonPress(letter: Letter) {
        if (correct) return

        if (buttons.size == sequence.size) {
            buttons.removeAt(0)
        }
        buttons.add(buttons.size, letter)

        colors = setColors()
    }

    /**
     * create a list of letters (either null or user input) the same size as sequence list
     *
     * @return a list of colors per each letter list item
     */
    private fun setColors(): MutableList<Color> {
        val letters = mutableListOf<Letter?>()

        if (buttons.size != sequence.size) {
            // calc difference of list size for button press / sequence
            val delta = sequence.size - buttons.size
            for (i in 0 until delta) {
                letters.add(null)
            }
        }
        buttons.forEach {
            letters.add(it)
        }

        val colors = mutableListOf<Color>()
        letters.forEachIndexed { index, letter ->
            colors.add(when {
                letter == null -> DARK_GREY
                sequence[index] == letter -> GREEN
                sequence.contains(letter) -> ORANGE
                else -> RED
            })
        }

        return colors
    }

}