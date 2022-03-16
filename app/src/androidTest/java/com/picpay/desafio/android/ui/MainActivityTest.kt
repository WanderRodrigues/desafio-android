package com.picpay.desafio.android.ui

import android.view.View
import android.widget.AdapterView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.picpay.desafio.android.R
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test


class MainActivityTest {


    //Activity to be tested
    @get:Rule
    val rule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
     fun checkTitle(){
        //Check if textView is showing
        onView(withId(R.id.title)).check(matches(isDisplayed()))
        //Check the text being displayed
        onView(withId(R.id.title)).check(matches(withText(R.string.title)))

     }

    @Test
    fun checksRecyclerViewIsDisplayed(){
        //Time to load users
        Thread.sleep(2000)
        onView(withId(R.id.recyclerUsers)).check(matches(isDisplayed()))
        //Checking if when the recycler view is visible the progress bar does not appear
        onView(withId(R.id.progressBarUsers)).check(matches(not(isDisplayed())))

    }


    @Test
    fun checkScrollView(){
        //Time to load users
        Thread.sleep(2000)

        //Scroll to view
        onView(withId(R.id.nestedScrollView))
            .perform(
               ViewActions.swipeUp()
            )
        Thread.sleep(2000)

        //Checking if the title is no longer appearing in the view, because when the screen is scrolled the title over junt
        onView(withId(R.id.title)).check(matches(not(isDisplayed())))
    }

    @Test
    fun checksProgressBarIsDisplayed(){
        onView(withId(R.id.progressBarUsers)).check(matches(isDisplayed()))
        //Checking that when the progress bar is visible the recycler view does not appear
        onView(withId(R.id.recyclerUsers)).check(matches(not(isDisplayed())))
    }

}