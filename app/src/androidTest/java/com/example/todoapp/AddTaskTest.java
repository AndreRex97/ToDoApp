package com.example.todoapp;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.intent.IntentCallback;
import androidx.test.runner.intent.IntentMonitorRegistry;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.google.android.apps.common.testing.accessibility.framework.replacements.Uri;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;

import java.io.IOException;
import java.io.OutputStream;

@LargeTest
public class AddTaskTest {
    private String TC001_taskName;
    private String TC002_taskName;
    private String TC003_taskName;
    private String TC004_taskName;
    private int TC002_month;
    private int TC002_day;
    private int TC002_year;
    private int TC004_month;
    private int TC004_day;
    private int TC004_year;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString(){
        TC001_taskName = "Add Task 1";
        TC002_taskName = "Add Task 2";
        TC003_taskName = "Add Task 3";
        TC004_taskName = "Add Task 4";

        TC002_month = 3;
        TC002_day = 28;
        TC002_year = 2022;

        TC004_month = 3;
        TC004_day = 29;
        TC004_year = 2022;

    }


    @Test
    public void TC001() throws InterruptedException {
        //Click on pencil icon and enter task name
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.newTaskText))
                .perform(typeText(TC001_taskName),closeSoftKeyboard());
        // Click on save button
        onView(withId(R.id.newTaskButton)).perform(click());

        SystemClock.sleep(3000);
        // Check that task is submitted successfully
        onView(ViewMatchers.withId(R.id.tasksRecyclerView))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(TC001_taskName))
                ));
    }

    @Test
    public void TC002() throws InterruptedException {
        //Click on pencil icon and enter task name
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.newTaskText))
                .perform(typeText(TC002_taskName),closeSoftKeyboard());

        //Click on set due date button
        onView(withId(R.id.datePickerButton))
                .perform(click());
        //onView(withId(R.id.newTaskButton)).perform(click());

        SystemClock.sleep(3000);

        //select date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(TC002_year, TC002_month, TC002_day));
        //Click OK button after selecting date
        onView(withText("OK"))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()))
                .perform(click());

        // Click on save button
        onView(withId(R.id.newTaskButton)).perform(click());

        // Check that task is submitted successfully
        onView(ViewMatchers.withId(R.id.tasksRecyclerView))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(TC002_taskName))
                ));

    }

    @Test
    public void TC003() throws InterruptedException {

        //Click on pencil icon and enter task name
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.newTaskText))
                .perform(typeText(TC003_taskName),closeSoftKeyboard());

        //Click on add image button

       /* onView(withId(R.id.cameraButton))
                .perform(click());*/

        //Click on camera button

        //Click on "tick" button

        // Click on save button
        onView(withId(R.id.newTaskButton)).perform(click());

        // Check that task is submitted successfully
        onView(ViewMatchers.withId(R.id.tasksRecyclerView))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(TC003_taskName))
                ));

    }


    @Test
    public void TC004() throws InterruptedException {

        //Click on pencil icon and enter task name
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.newTaskText))
                .perform(typeText(TC004_taskName),closeSoftKeyboard());

        //Click on add image button

       /* onView(withId(R.id.cameraButton))
                .perform(click());*/

        //Click on camera button

        //Click on "tick" button

        //Click on set due date button
        onView(withId(R.id.datePickerButton))
                .perform(click());
        //onView(withId(R.id.newTaskButton)).perform(click());

        SystemClock.sleep(3000);

        //select date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(TC004_year, TC004_month, TC004_day));
        //Click OK button after selecting date
        onView(withText("OK"))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()))
                .perform(click());


        // Click on save button
        onView(withId(R.id.newTaskButton)).perform(click());

        // Check that task is submitted successfully
        onView(ViewMatchers.withId(R.id.tasksRecyclerView))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(TC004_taskName))
                ));

    }


}
