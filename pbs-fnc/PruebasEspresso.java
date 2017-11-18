package com.example.carlos.wumpusproject;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.example.carlos.wumpusproject.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.carlos.wumpusproject.Pruebas.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

//Estas son las pruebas funcionales que se le hacen al programa
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

        SystemClock.sleep(1000);
        onView(withId(R.id.PoliRegButton)) //Entra a ver poligonos regulares
                .perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.Tetraedro)) //Click en la imagen del tetrahedro
                .perform(click());
        SystemClock.sleep(2000);
        onView(isRoot()).perform(ViewActions.pressBack()); //Retorna
        SystemClock.sleep(2000);

        onView(withId(R.id.Octaedro)) //Click en la imagen del octahedro
                .perform(click());
        SystemClock.sleep(2000);
        onView(isRoot()).perform(ViewActions.pressBack()); //Retorna
        SystemClock.sleep(2000);

        onView(withId(R.id.Cubo)) //Click en la imagen del cubo
                .perform(click());
        SystemClock.sleep(2000);
        onView(isRoot()).perform(ViewActions.pressBack()); //Retorna
        SystemClock.sleep(2000);

        onView(withId(R.id.Icosaedro)) //Click en la imagen del icosahedro
                .perform(click());
        SystemClock.sleep(2000);
        onView(isRoot()).perform(ViewActions.pressBack()); //Retorna
        SystemClock.sleep(2000);

        onView(withId(R.id.Dodecaedro)) //Click en la imagen del dodecahedro
                .perform(click());
        SystemClock.sleep(4000);
        onView(isRoot()).perform(ViewActions.pressBack()); //Retorna
        SystemClock.sleep(2000);

        onView(isRoot()).perform(ViewActions.pressBack()); //Vuelve al menú principal
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


        //hace un laberinto con sentido
        onView(withId(R.id.imageButton10)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton72)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton72)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton710)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton72)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton34)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton34)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton38)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton38)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton710)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton72)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton116)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton710)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton116)) //hasta esta otra
                .perform(click());

        SystemClock.sleep(300);
        onView(withId(R.id.imageButton56)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton116)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton56)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton710)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton56)) //Arista que va desde esta cueva
                .perform(click());
        SystemClock.sleep(300);
        onView(withId(R.id.imageButton72)) //hasta esta otra
                .perform(click());
        SystemClock.sleep(1000);
        //Termina de hacer un laberinto con sentido


        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.finalizeDrawButton), withText("Guardar Laberinto"), isDisplayed()));
        appCompatButton2.perform(click()); //Click en el botón para guardar el laberinto
        SystemClock.sleep(500);

        ViewInteraction editText = onView(
                allOf(withClassName(is("android.widget.EditText")),
                        withParent(allOf(withId(R.id.custom),
                                withParent(withId(R.id.customPanel)))),
                        isDisplayed()));
        editText.perform(replaceText("prueba"), closeSoftKeyboard()); //Le pone nombre
        SystemClock.sleep(500);
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Guardar"))); //Lo guarda
        appCompatButton3.perform(scrollTo(), click());
        SystemClock.sleep(500);

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton4.perform(scrollTo(), click());
        SystemClock.sleep(500);


        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.elegirBiblio), withText("Elegir de la Biblioteca"), isDisplayed()));
        appCompatButton5.perform(click()); //Busca el laberinto guardado en la biblioteca
        SystemClock.sleep(500);

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("prueba"), //Lo elige
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        withParent(withId(R.id.contentPanel))), 0),
                        isDisplayed()));
        appCompatTextView.perform(click());
        SystemClock.sleep(500);

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Aceptar")));
        appCompatButton6.perform(scrollTo(), click());


        //Pruebas de Bluetooth
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.compartirBluetooth), withText("Compartir por Bluetooth"), isDisplayed()));
        appCompatButton7.perform(click()); //Entra a la parte de Bluetooth
        SystemClock.sleep(500);
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnLaberinto), withText("Seleccionar Laberinto Para Enviar"), isDisplayed()));
        appCompatButton8.perform(click()); //Muestra lista de laberintos para enviar uno
        SystemClock.sleep(500);
        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button2), withText("Cancel")));
        appCompatButton9.perform(scrollTo(), click()); //Cancela
        SystemClock.sleep(500);
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.sendBtooth), withText("Enviar"), isDisplayed()));
        appCompatButton10.perform(click()); //Intenta enviar, pero debe mandar error
        SystemClock.sleep(500);
        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.btnSeleccionar), withText("Seleccionar laberinto recibido"), isDisplayed()));
        appCompatButton11.perform(click()); //Entra a seleccionar un laberinto recibido
        SystemClock.sleep(1000);
        //onView(isRoot()).perform(ViewActions.pressBack()); //Retorna
        SystemClock.sleep(1000);
        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.btnRecibido), withText("Guardar Laberinto Recibido en Biblioteca"), isDisplayed()));
        appCompatButton12.perform(click()); //Lo guarda
        SystemClock.sleep(500);
        ViewInteraction appCompatButton13 = onView(
                allOf(withId(android.R.id.button1), withText("Aceptar")));
        appCompatButton13.perform(scrollTo(), click());
        SystemClock.sleep(500);
        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.BTNquit), withText("Salir"), isDisplayed()));
        appCompatButton14.perform(click()); //Se devuelve
        SystemClock.sleep(2000);

        onView(withId(R.id.emplazar)) //Emplaza el laberinto irregular
                .perform(click());
        SystemClock.sleep(1000);
    }
}
