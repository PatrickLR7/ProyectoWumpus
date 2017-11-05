package com.example.carlos.wumpusproject;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.carlos.wumpusproject.activity.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class Pruebas {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void pruebas() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.LabIrregButton), withText("Laberinto Irregular"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.imageButton10), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.imageButton38), isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.imageButton10), isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.imageButton72), isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.imageButton72), isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.imageButton34), isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.imageButton34), isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withId(R.id.imageButton38), isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction appCompatImageButton9 = onView(
                allOf(withId(R.id.imageButton38), isDisplayed()));
        appCompatImageButton9.perform(click());

        ViewInteraction appCompatImageButton10 = onView(
                allOf(withId(R.id.imageButton710), isDisplayed()));
        appCompatImageButton10.perform(click());

        ViewInteraction appCompatImageButton11 = onView(
                allOf(withId(R.id.imageButton72), isDisplayed()));
        appCompatImageButton11.perform(click());

        ViewInteraction appCompatImageButton12 = onView(
                allOf(withId(R.id.imageButton710), isDisplayed()));
        appCompatImageButton12.perform(click());

        ViewInteraction appCompatImageButton13 = onView(
                allOf(withId(R.id.imageButton116), isDisplayed()));
        appCompatImageButton13.perform(click());

        ViewInteraction appCompatImageButton14 = onView(
                allOf(withId(R.id.imageButton710), isDisplayed()));
        appCompatImageButton14.perform(click());

        ViewInteraction appCompatImageButton15 = onView(
                allOf(withId(R.id.imageButton116), isDisplayed()));
        appCompatImageButton15.perform(click());

        ViewInteraction appCompatImageButton16 = onView(
                allOf(withId(R.id.imageButton72), isDisplayed()));
        appCompatImageButton16.perform(click());

        ViewInteraction appCompatImageButton17 = onView(
                allOf(withId(R.id.imageButton116), isDisplayed()));
        appCompatImageButton17.perform(click());

        ViewInteraction appCompatImageButton18 = onView(
                allOf(withId(R.id.imageButton56), isDisplayed()));
        appCompatImageButton18.perform(click());

        ViewInteraction appCompatImageButton19 = onView(
                allOf(withId(R.id.imageButton56), isDisplayed()));
        appCompatImageButton19.perform(click());

        ViewInteraction appCompatImageButton20 = onView(
                allOf(withId(R.id.imageButton72), isDisplayed()));
        appCompatImageButton20.perform(click());

        ViewInteraction appCompatImageButton21 = onView(
                allOf(withId(R.id.imageButton56), isDisplayed()));
        appCompatImageButton21.perform(click());

        ViewInteraction appCompatImageButton22 = onView(
                allOf(withId(R.id.imageButton710), isDisplayed()));
        appCompatImageButton22.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.finalizeDrawButton), withText("Guardar Grafo"), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction editText = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText.perform(replaceText("prueba-a"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Guardar")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.elegirBiblio), withText("Elegir de la Biblioteca"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("prueba-a"),
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        withParent(withId(R.id.contentPanel))),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Aceptar")));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.compartirBluetooth), withText("Compartir por Bluetooth"), isDisplayed()));
        appCompatButton7.perform(click());

    }

    public static Matcher<View> childAtPosition(
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
