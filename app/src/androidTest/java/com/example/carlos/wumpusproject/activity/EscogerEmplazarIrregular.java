package com.example.carlos.wumpusproject.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.carlos.wumpusproject.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EscogerEmplazarIrregular {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void escogerEmplazarIrregular() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.LabIrregButton), withText("Laberinto Irregular"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.imageButton10),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.imageButton18),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        1),
                                8),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.imageButton10),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.imageButton112),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        11),
                                2),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.imageButton310),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        3),
                                10),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.imageButton56),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        5),
                                6),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.imageButton112),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        11),
                                2),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withId(R.id.imageButton56),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        5),
                                6),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction appCompatImageButton9 = onView(
                allOf(withId(R.id.imageButton18),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        1),
                                8),
                        isDisplayed()));
        appCompatImageButton9.perform(click());

        ViewInteraction appCompatImageButton10 = onView(
                allOf(withId(R.id.imageButton56),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        5),
                                6),
                        isDisplayed()));
        appCompatImageButton10.perform(click());

        ViewInteraction appCompatImageButton11 = onView(
                allOf(withId(R.id.imageButton18),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        1),
                                8),
                        isDisplayed()));
        appCompatImageButton11.perform(click());

        ViewInteraction appCompatImageButton12 = onView(
                allOf(withId(R.id.imageButton310),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        3),
                                10),
                        isDisplayed()));
        appCompatImageButton12.perform(click());

        ViewInteraction appCompatImageButton13 = onView(
                allOf(withId(R.id.imageButton10),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageButton13.perform(click());

        ViewInteraction appCompatImageButton14 = onView(
                allOf(withId(R.id.imageButton56),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        5),
                                6),
                        isDisplayed()));
        appCompatImageButton14.perform(click());

        ViewInteraction appCompatImageButton15 = onView(
                allOf(withId(R.id.imageButton96),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        9),
                                6),
                        isDisplayed()));
        appCompatImageButton15.perform(click());

        ViewInteraction appCompatImageButton16 = onView(
                allOf(withId(R.id.imageButton112),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        11),
                                2),
                        isDisplayed()));
        appCompatImageButton16.perform(click());

        ViewInteraction appCompatImageButton17 = onView(
                allOf(withId(R.id.imageButton310),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        3),
                                10),
                        isDisplayed()));
        appCompatImageButton17.perform(click());

        ViewInteraction appCompatImageButton18 = onView(
                allOf(withId(R.id.imageButton96),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonMatrix),
                                        9),
                                6),
                        isDisplayed()));
        appCompatImageButton18.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.emplazar), withText("Emplazar laberinto"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.setRangeButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
