package com.dino.great

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dino.great.module.list.ScrollingActivity
import com.dino.great.module.list.ViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PostListFragmentTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(ScrollingActivity::class.java)

    @Test
    fun test_isPostFragmentVisible(){
        onView(withId(R.id.posts)).check(matches(isDisplayed()))
    }
    @Test
    fun test_selectPost(){
        onView(withId(R.id.posts)).perform(actionOnItemAtPosition<ViewHolder>(0, click()))
    }
}