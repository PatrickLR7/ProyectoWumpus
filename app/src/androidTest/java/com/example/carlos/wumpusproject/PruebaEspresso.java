package com.example.carlos.wumpusproject;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class PruebaEspresso extends ActivityInstrumentationTestCase2<MainActivity>
{

    public PruebaEspresso()
    {
        super(MainActivity.class);
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
        SystemClock.sleep(1000);
        onView(withId(R.id.PoliRegButton)) //Entra a ver poligonos regulares
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.Tetraedro)) //Click en la imagen del tetrahedro
                .perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.Octaedro)) //Click en la imagen del octahedro
                .perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.Cubo)) //Click en la imagen del cubo
                .perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.Icosaedro)) //Click en la imagen del icosahedro
                .perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.Dodecaedro)) //Click en la imagen del dodecahedro
                .perform(click());
        SystemClock.sleep(4000);

        onView(isRoot()).perform(ViewActions.pressBack()); //Retorna
        SystemClock.sleep(2000);
        onView(withId(R.id.LabIrregButton)) //Ahora entra a dibujar laberinto
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton10)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton38)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton32)) //Intenta hacer una arista
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton32)) //sobre ella misma
            .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton1100)) //Arista que va desde 1100
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton1110)) //hasta 1110
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton1100)) //Intenta hacer una arista
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton1110)) //paralela
                .perform(click());
        SystemClock.sleep(1000);

        //hace un laberinto con sentido
        onView(withId(R.id.imageButton10)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton72)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton72)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton710)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton72)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton52)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton52)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton36)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton36)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton38)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton38)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton710)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(1000);
        //Termina de hacer un laberinto con sentido


    }
}
