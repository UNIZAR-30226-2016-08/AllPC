package es.unizar.eina.allpc;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Rule;

import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Created by adria on 24/05/2016.
 */
@RunWith(AndroidJUnit4.class)
public class EspressoAllPC_AllTest
{

    private static final String TEST_ADMIN_USER="admin@admin.com";
    private static final String TEST_ADMIN_PASS="admin";

    private static final String TEST_PC_MODELO="PC TEST";
    private static final String TEST_PC_MARCA="Test";
    private static final String TEST_PC_PROCESADOR="i7 Test";
    private static final String TEST_PC_SO="Test SO";
    private static final String TEST_PC_GRAFICA="630M Test";
    private static final String TEST_PC_CONEXIONES="USB 3.0 x5";

    private static final String TEST_PC_EDIT=" MOD";
    private static final String TEST_PC_MODELO_MOD="PC TEST MOD";

    private static final String TEST_PC_MODELO2="PC TEST 2";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAPP(){
        /*-----------------------------------USUARIO ADMIN----------------------------------------*/
        /*-----------------LOGIN-------------------*/
        //Entrar en menu y pulsar login
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_login)).check(matches(notNullValue()));
        onView(withText(R.string.menu_login)).perform(click());

        // Inserta el correo y pass del admin
        onView(withId(R.id.email)).perform(typeText(TEST_ADMIN_USER), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(TEST_ADMIN_PASS), closeSoftKeyboard());

        // Confirma y vuelve a la actividad anterior
        onView(withId(R.id.email_sign_in_button)).perform(click());


        /*-----------------CREAR PC 1-----------------*/
        //Entrar en menu y pulsar crear PC
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_crearPC)).check(matches(notNullValue()));
        onView(withText(R.string.menu_crearPC)).perform(click());

        // Rellena los campos del PC
        onView(withId(R.id.modelo)).perform(typeText(TEST_PC_MODELO), closeSoftKeyboard());
        onView(withId(R.id.marca)).perform(typeText(TEST_PC_MARCA), closeSoftKeyboard());
        onView(withId(R.id.procesador)).perform(typeText(TEST_PC_PROCESADOR), closeSoftKeyboard());
        onView(withId(R.id.so)).perform(typeText(TEST_PC_SO), closeSoftKeyboard());
        onView(withId(R.id.grafica)).perform(typeText(TEST_PC_GRAFICA), closeSoftKeyboard());
        onView(withId(R.id.conexiones)).perform(typeText(TEST_PC_CONEXIONES), closeSoftKeyboard());


        // Confirma y vuelve a la actividad anterior
        onView(withId(R.id.confirm)).perform(click());


        /*-----------------CREAR PC 2-----------------*/
        //Entrar en menu y pulsar crear PC
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_crearPC)).check(matches(notNullValue()));
        onView(withText(R.string.menu_crearPC)).perform(click());

        // Rellena los campos del PC
        onView(withId(R.id.modelo)).perform(typeText(TEST_PC_MODELO2), closeSoftKeyboard());
        onView(withId(R.id.marca)).perform(typeText(TEST_PC_MARCA), closeSoftKeyboard());
        onView(withId(R.id.procesador)).perform(typeText(TEST_PC_PROCESADOR), closeSoftKeyboard());
        onView(withId(R.id.so)).perform(typeText(TEST_PC_SO), closeSoftKeyboard());
        onView(withId(R.id.grafica)).perform(typeText(TEST_PC_GRAFICA), closeSoftKeyboard());
        onView(withId(R.id.conexiones)).perform(typeText(TEST_PC_CONEXIONES), closeSoftKeyboard());


        // Confirma y vuelve a la actividad anterior
        onView(withId(R.id.confirm)).perform(click());


        /*-----------------EDITAR PC 1-----------------*/
        // Busca y edita el PC insertado
        onView(withText(TEST_PC_MODELO)).perform(longClick());
        onView(withText(R.string.menu_editar)).perform(click());

        // Modifica el modelo del PC
        onView(withId(R.id.modelo)).perform(typeText(TEST_PC_EDIT), closeSoftKeyboard());

        // Confirma y vuelve a la actividad anterior
        onView(withId(R.id.confirm)).perform(click());

        /*-----------------CERRAR SESION-------------------*/
        //Entrar en menu y pulsar cerrar sesion
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_logout)).check(matches(notNullValue()));
        onView(withText(R.string.menu_logout)).perform(click());

        /*-----------------------------------USUARIO NORMAL--------------------------------------*/
        /*-------------ORDENAR ASC--------------*/
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_order_asc)).check(matches(notNullValue()));
        onView(withText(R.string.menu_order_asc)).perform(click());

        /*-------------ORDENAR DESC--------------*/
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_order_desc)).check(matches(notNullValue()));
        onView(withText(R.string.menu_order_desc)).perform(click());

        /*-----------------VER PC-------------------*/
        // Busca y visualiza el pc
        onView(withText(TEST_PC_MODELO_MOD)).perform(longClick());
        onView(withText(R.string.menu_ver)).perform(click());

        onView(isRoot()).perform(pressBack());

        /*-----------AÑADIR AL COMPARADOR----------*/
        // Busca y añade al comparador un pc
        onView(withText(TEST_PC_MODELO_MOD)).perform(longClick());
        onView(withText(R.string.menu_compare)).perform(click());

        onView(withText(TEST_PC_MODELO2)).perform(longClick());
        onView(withText(R.string.menu_compare)).perform(click());

        /*-------------VER COMPARADOR--------------*/
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_comparador)).check(matches(notNullValue()));
        onView(withText(R.string.menu_comparador)).perform(click());

        onView(isRoot()).perform(pressBack());


        /*-----------------------------------USUARIO ADMIN----------------------------------------*/
        /*-----------------LOGIN-------------------*/
        //Entrar en menu y pulsar login
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_login)).check(matches(notNullValue()));
        onView(withText(R.string.menu_login)).perform(click());

        // Inserta el correo y pass del admin
        onView(withId(R.id.email)).perform(typeText(TEST_ADMIN_USER), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(TEST_ADMIN_PASS), closeSoftKeyboard());

        // Confirma y vuelve a la actividad anterior
        onView(withId(R.id.email_sign_in_button)).perform(click());

        /*-----------------BORRAR PC-------------------*/
        // Busca y edita el PC insertado
        onView(withText(TEST_PC_MODELO_MOD)).perform(longClick());
        onView(withText(R.string.menu_borrar)).perform(click());

        onView(withText(TEST_PC_MODELO2)).perform(longClick());
        onView(withText(R.string.menu_borrar)).perform(click());

        /*-----------------CERRAR SESION-------------------*/
        //Entrar en menu y pulsar cerrar sesion
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.menu_logout)).check(matches(notNullValue()));
        onView(withText(R.string.menu_logout)).perform(click());
    }
}
