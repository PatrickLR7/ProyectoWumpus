package com.example.carlos.wumpusproject;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.carlos.wumpusproject.Pruebas.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

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
    public void probar()
    {
        getActivity();
        /*
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
        */
        SystemClock.sleep(2000);
        onView(withId(R.id.LabIrregButton)) //Ahora entra a dibujar laberinto
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.imageButton10)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton38)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(500);

        /* Pruebas de validación
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
        */

        //hace un laberinto con sentido
        onView(withId(R.id.imageButton10)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton72)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton72)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton710)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton72)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton34)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton34)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton38)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton38)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton710)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton72)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton116)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton710)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton116)) //hasta esta otra
                .perform(click());

        SystemClock.sleep(500);
        onView(withId(R.id.imageButton56)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton116)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton56)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton710)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton56)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.imageButton72)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(2000);
        //Termina de hacer un laberinto con sentido

        postLab();
    }

    public void postLab()
    {
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.finalizeDrawButton), withText("Guardar Grafo"), isDisplayed()));
        appCompatButton2.perform(click());
        SystemClock.sleep(500);

        ViewInteraction editText = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText.perform(replaceText("prueba-a"), closeSoftKeyboard());
        SystemClock.sleep(500);
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Guardar")));
        appCompatButton3.perform(scrollTo(), click());
        SystemClock.sleep(500);

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton4.perform(scrollTo(), click());
        SystemClock.sleep(500);

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.elegirBiblio), withText("Elegir de la Biblioteca"), isDisplayed()));
        appCompatButton5.perform(click());
        SystemClock.sleep(500);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("prueba-a"),
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        withParent(withId(R.id.contentPanel))),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());
        SystemClock.sleep(500);

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Aceptar")));
        appCompatButton6.perform(scrollTo(), click());

        /* Bluetooth no está listo
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.compartirBluetooth), withText("Compartir por Bluetooth"), isDisplayed()));
        appCompatButton7.perform(click());
        SystemClock.sleep(500);
        onView(isRoot()).perform(ViewActions.pressBack()); //Retorna
        */
        SystemClock.sleep(1000);
    }
}
