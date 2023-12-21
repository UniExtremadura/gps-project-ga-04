package es.unex.giiis.fitlife365.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.ExerciseModel
import es.unex.giiis.fitlife365.model.Routine
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestHU10 {
    private lateinit var exerciseModel: ExerciseModel
    private lateinit var routine: Routine

    @Before
    fun setup() {
        exerciseModel = ExerciseModel(
            exerciseId = 1,
            name = "Push-up",
            type = "Strength",
            muscle = "Chest",
            equipment = "None",
            difficulty = "Intermediate",
            instructions = "Do a push-up",
            isSelected = false,
            routineId = 1,
            isCompleted = false
        )
        routine = Routine(
            routineId = 1,
            userId = 1,
            name = "Monday Routine",
            pesoObjetivo = 70,
            diasEntrenamiento = "Monday",
            ejercicios = "1"
        )
    }
    @Test
    fun probarCompletado() {
        if (routine.ejercicios == exerciseModel.exerciseId.toString()) {
            if(!exerciseModel.isCompleted) {
                assert(!exerciseModel.isCompleted)
                exerciseModel.isCompleted = true
                assert(exerciseModel.isCompleted)
            }
            else {
                assert(exerciseModel.isCompleted)
                exerciseModel.isCompleted = false
                assert(!exerciseModel.isCompleted)
            }
        }
    }
/*
    //-----------------------------------TEST DE ESPRESSO-------------------------------------------

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testHU10() {
        val materialButton = onView(
            allOf(
                withId(R.id.registrateButton), withText("Regístrate"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.registerUsername),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.registerEmail),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("test@gmail.com"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.registerPassword),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("test"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.registerConfirmPassword),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("test"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.idContinuarRegistro), withText("Continuar"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val materialButton3 = onView(
            allOf(
                withId(R.id.btnAceptar), withText("Aceptar"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialButton3.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.imageViewResultado),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.nav_create_routine), withContentDescription("Crear Rutina"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottom_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val materialButton4 = onView(
            allOf(
                withId(R.id.btnAceptar), withText("Aceptar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_containerHome),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val appCompatSpinner = onView(
            allOf(
                withId(R.id.spinnerMusculo),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_containerHome),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatSpinner.perform(click())

        val appCompatCheckedTextView = onData(anything())
            .inAdapterView(
                childAtPosition(
                    withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                    0
                )
            )
            .atPosition(1)
        appCompatCheckedTextView.perform(click())

        val materialCheckBox = onView(
            allOf(
                withId(R.id.checkBoxNombre), withText("Añadir a la rutina"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.rvListaEjercicios),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialCheckBox.perform(click())

        val materialButton5 = onView(
            allOf(
                withId(R.id.btnGuardarEnRutina), withText("Guardar en Rutina"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_containerHome),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton5.perform(click())

        val materialButton6 = onView(
            allOf(
                withId(R.id.btnConfirmarEjercicios), withText("Confirmar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_containerHome),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        materialButton6.perform(click())

        val materialButton7 = onView(
            allOf(
                withId(R.id.btnRutina), withText("Por defecto"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton7.perform(click())

        val materialCheckBox2 = onView(
            allOf(
                withId(R.id.ejercicioCompletadoCheckBox), withText("Ejercicio completado"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerViewEjercicios),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialCheckBox2.perform(click())

        val materialButton8 = onView(
            allOf(
                withId(R.id.btnVolverHome), withText("Mis Rutinas"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayoutButtons),
                        childAtPosition(
                            withClassName(`is`("android.widget.RelativeLayout")),
                            3
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton8.perform(click())

        val materialButton9 = onView(
            allOf(
                withId(R.id.btnRutina), withText("Por defecto"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recyclerView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton9.perform(click())

        val checkBox = onView(
            allOf(
                withId(R.id.ejercicioCompletadoCheckBox), withText("Ejercicio completado"),
                withParent(withParent(withId(R.id.recyclerViewEjercicios))),
                isDisplayed()
            )
        )
        checkBox.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

 */
}
