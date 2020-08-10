package com.alexzh.company.contacts

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.alexzh.company.contacts.teams.TeamsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TeamsActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule<TeamsActivity>(TeamsActivity::class.java)

    @Test
    fun shouldBeVisible() {
        onView(withId(R.id.teamsRecyclerView))
                .check(matches(isDisplayed()))
    }
}