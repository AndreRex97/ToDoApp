package com.example.todoapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class AddTaskTest {
    private String taskName;
    private String taskDescription;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString(){
        taskName = "Test Task Name";
        taskDescription = "Test Task Description";
    }

    @Test
    public void create_task(){

        //Enter task name
        onView(withId(R.id.newTaskText))
                .perform(replaceText(""))
                .perform(typeText(taskName), closeSoftKeyboard() );

        // Click on new task button
        onView(withId(R.id.newTaskButton)).perform(click());

        // Check that task is submitted successfully
       /* onView(withId(R.id.audTextView))
                .check(matches(withText(audString)));*/
    }


}
