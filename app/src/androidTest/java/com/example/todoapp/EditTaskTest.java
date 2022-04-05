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
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

import android.os.SystemClock;
import android.view.View;
import android.widget.DatePicker;

public class EditTaskTest {

    private String editTaskName;
    private int TC008_month;
    private int TC008_day;
    private int TC008_year;
    private int TC009_month;
    private int TC009_day;
    private int TC009_year;
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        editTaskName = "Edit Task 1";
        TC008_month = 3;
        TC008_day = 30;
        TC008_year = 2022;

        TC009_month = 3;
        TC009_day = 31;
        TC009_year = 2022;
    }

    @Test
    public void TC005() throws InterruptedException {
        //Swipe right to edit
        onView(withId(R.id.tasksRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, new GeneralSwipeAction(
                        Swipe.SLOW, GeneralLocation.BOTTOM_LEFT, GeneralLocation.BOTTOM_RIGHT,
                        Press.FINGER)));
        SystemClock.sleep(2000);
        onView(withId(R.id.newTaskText)).perform(replaceText(editTaskName),closeSoftKeyboard());

        onView(withId(R.id.newTaskButton)).perform(click());

        onView(ViewMatchers.withId(R.id.tasksRecyclerView))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(editTaskName))
                ));

        SystemClock.sleep(3000);
        //onView(withId(R.id.newTaskButton)).perform(click());
    }

    @Test
    public void TC006() throws InterruptedException {
        //Swipe right to edit
        onView(withId(R.id.tasksRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, new GeneralSwipeAction(
                        Swipe.SLOW, GeneralLocation.BOTTOM_LEFT, GeneralLocation.BOTTOM_RIGHT,
                        Press.FINGER)));
        SystemClock.sleep(2000);
        onView(withId(R.id.newTaskText)).perform(replaceText(editTaskName),closeSoftKeyboard());

        //Add image code here

        onView(withId(R.id.newTaskButton)).perform(click());

        onView(ViewMatchers.withId(R.id.tasksRecyclerView))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(editTaskName))
                ));

        SystemClock.sleep(5000);
        //onView(withId(R.id.newTaskButton)).perform(click());
    }

    @Test
    public void TC007() throws InterruptedException {
        //Swipe right to edit
        onView(withId(R.id.tasksRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, new GeneralSwipeAction(
                        Swipe.SLOW, GeneralLocation.BOTTOM_LEFT, GeneralLocation.BOTTOM_RIGHT,
                        Press.FINGER)));
        SystemClock.sleep(2000);
        onView(withId(R.id.newTaskText)).perform(replaceText(editTaskName),closeSoftKeyboard());

        //Add image code here

        onView(withId(R.id.newTaskButton)).perform(click());

        onView(ViewMatchers.withId(R.id.tasksRecyclerView))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText(editTaskName))
                ));

        SystemClock.sleep(5000);
        //onView(withId(R.id.newTaskButton)).perform(click());
    }

    @Test
    public void TC008() throws InterruptedException {
        //Swipe right to edit
        onView(withId(R.id.tasksRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, new GeneralSwipeAction(
                        Swipe.SLOW, GeneralLocation.BOTTOM_LEFT, GeneralLocation.BOTTOM_RIGHT,
                        Press.FINGER)));
        SystemClock.sleep(2000);


        //Click on set due date button
        onView(withId(R.id.datePickerButton))
                .perform(click());

        SystemClock.sleep(2000);

        //select date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(TC008_year, TC008_month, TC008_day));
        //Click OK button after selecting date
        onView(withText("OK"))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()))
                .perform(click());

        // Click on save button
        onView(withId(R.id.newTaskButton)).perform(click());

        SystemClock.sleep(5000);
        //onView(withId(R.id.newTaskButton)).perform(click());
    }

    @Test
    public void TC009() throws InterruptedException {
        //Swipe right to edit
        onView(withId(R.id.tasksRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, new GeneralSwipeAction(
                        Swipe.SLOW, GeneralLocation.BOTTOM_LEFT, GeneralLocation.BOTTOM_RIGHT,
                        Press.FINGER)));
        SystemClock.sleep(2000);


        //Click on set due date button
        onView(withId(R.id.datePickerButton))
                .perform(click());

        SystemClock.sleep(2000);

        //select date
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(TC009_year, TC009_month, TC009_day));
        //Click OK button after selecting date
        onView(withText("OK"))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()))
                .perform(click());

        // Click on save button
        onView(withId(R.id.newTaskButton)).perform(click());

        SystemClock.sleep(5000);
        //onView(withId(R.id.newTaskButton)).perform(click());
    }
}
