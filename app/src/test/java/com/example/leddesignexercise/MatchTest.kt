package com.example.leddesignexercise

import com.example.leddesignexercise.data.model.Color.*
import com.example.leddesignexercise.data.model.Letter.*
import com.example.leddesignexercise.data.Match
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class MatchTest {

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `empty button press list`() {
        val sequence = listOf(A, B, A)
        val m = Match(sequence)

        assert(m.buttons.isEmpty())
    }

    @Test
    fun `static button press list size`() {
        val m = Match(listOf(A, B, A))

        assert(m.buttons.isEmpty())

        val first = A
        m.handleButtonPress(first)
        m.handleButtonPress(B)
        m.handleButtonPress(B)

        assert(m.buttons.size == 3)

        val recent = C
        m.handleButtonPress(recent)

        assert(m.buttons.size == 3)
        assertFalse(m.buttons.contains(first))
        assert(m.buttons[m.buttons.size - 1] == recent)
    }

    @Test
    fun `color list changes updates member variable "correct"`() {
        val m = Match(listOf(A, B, A))

        m.handleButtonPress(A)
        m.handleButtonPress(B)

        assertFalse(m.colors.all { it == GREEN })
        assertFalse(m.correct)

        m.handleButtonPress(A)

        assert(m.colors.all { it == GREEN })
        assert(m.correct)
    }

    @Test
    fun `button press increases list of colors`() {
        val m = Match(listOf(A, B, A))

        assert(m.buttons.isEmpty())

        with(m.colors) {
            val totalLEDsTurnedOff = this.filter { it == DARK_GREY }.count()
            assert(totalLEDsTurnedOff == 3)
        }

        m.handleButtonPress(A)
        m.handleButtonPress(A)

        with(m.colors) {
            val totalLEDsTurnedOn = this.filter { it != DARK_GREY }.count()
            assert(totalLEDsTurnedOn == 2)
        }
    }

    @Test
    fun `colors for button press list`() {
        val m = Match(listOf(A, B, A))

        with(m.colors) {
            assert(this[0] == DARK_GREY)
            assert(this[1] == DARK_GREY)
            assert(this[2] == DARK_GREY)
        }

        m.handleButtonPress(B)
        with(m.colors) {
            assert(this[0] == DARK_GREY)
            assert(this[1] == DARK_GREY)
            assert(this[2] == ORANGE)
        }

        m.handleButtonPress(B)
        with(m.colors) {
            assert(this[0] == DARK_GREY)
            assert(this[1] == GREEN)
            assert(this[2] == ORANGE)
        }

        m.handleButtonPress(C)
        with(m.colors) {
            assert(this[0] == ORANGE)
            assert(this[1] == GREEN)
            assert(this[2] == RED)
        }

        m.handleButtonPress(A)
        with(m.colors) {
            assert(this[0] == ORANGE)
            assert(this[1] == RED)
            assert(this[2] == GREEN)
        }

        m.handleButtonPress(B)
        with(m.colors) {
            assert(this[0] == RED)
            assert(this[1] == ORANGE)
            assert(this[2] == ORANGE)
        }
    }

}