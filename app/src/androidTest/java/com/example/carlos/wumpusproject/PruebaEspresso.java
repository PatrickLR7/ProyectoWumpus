package com.example.carlos.wumpusproject;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class PruebaEspresso extends ActivityInstrumentationTestCase2<GameModeActivity>
{

    public PruebaEspresso()
    {
        super(GameModeActivity.class);
    }


    @Before
    public void setUp() throws Exception
    {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }


    @Test
    public void pruebaPrimerasDosPantallas()
    {
        getActivity();
        onView(withId(R.id.individualMode)) //Entra al modo individual
            .perform(click());
        SystemClock.sleep(1000);
        onView(isRoot()).perform(ViewActions.pressBack()); //Retorna
        SystemClock.sleep(1000);
        onView(withId(R.id.socialMode)) //Ahora entra al modo social
                .perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.PoliRegButton)) //Entra a ver poligonos regulares
                .perform(click());
        SystemClock.sleep(1000);
        onView(isRoot()).perform(ViewActions.pressBack()); //Retorna
        SystemClock.sleep(1000);
        onView(withId(R.id.LabIrregButton)) //Ahora entra a dibujar laberinto
                .perform(click());
    }

/*
    @After
    public void tearDown() throws Exception
    {
        super.tearDown();
    }
*/
}
