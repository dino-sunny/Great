package com.dino.great

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dino.great.module.list.ScrollingActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UiTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<ScrollingActivity> =
        ActivityScenarioRule(ScrollingActivity::class.java)

    @Test
    fun postListUiTest() {
        onView(withId(R.id.animationView)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.posts)).check(matches(isDisplayed()))
        onView(withId(R.id.noInternetLayout)).check(matches(not(isDisplayed())))
    }
}
