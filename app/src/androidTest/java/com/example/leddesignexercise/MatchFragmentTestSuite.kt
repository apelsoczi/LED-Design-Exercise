package com.example.leddesignexercise

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.leddesignexercise.ui.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class MatchFragmentTestSuite {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun buttonPress() {
        onView(withId(R.id.led_three)).check(matches(withText("")))
        onView(withId(R.id.led_two)).check(matches(withText("")))
        onView(withId(R.id.led_one)).check(matches(withText("")))

        onView(withId(R.id.button_a)).perform(click())
        onView(withId(R.id.led_three)).check(matches(withText("A")))
        onView(withId(R.id.led_two)).check(matches(withText("")))
        onView(withId(R.id.led_one)).check(matches(withText("")))

        onView(withId(R.id.button_b)).perform(click())
        onView(withId(R.id.led_three)).check(matches(withText("B")))
        onView(withId(R.id.led_two)).check(matches(withText("A")))
        onView(withId(R.id.led_one)).check(matches(withText("")))

        onView(withId(R.id.button_c)).perform(click())
        onView(withId(R.id.led_three)).check(matches(withText("C")))
        onView(withId(R.id.led_two)).check(matches(withText("B")))
        onView(withId(R.id.led_one)).check(matches(withText("A")))
    }

}