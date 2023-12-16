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
import es.unex.giiis.fitlife365.data.Exercise
import es.unex.giiis.fitlife365.model.ExerciseModel
import es.unex.giiis.fitlife365.model.Routine
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestHU8 {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    fun getStaticExerciseList(): List<Exercise> {
        // Crea y devuelve una lista que contiene el ejercicio estático o mock
        val staticExercise = Exercise(
            name = "Static Exercise",
            muscle = "Biceps",
            equipment = "Dumbbell",
            difficulty = "beginner",
            type = "Strength",
            instructions = "Instructions for static exercise"
        )

        return listOf(staticExercise)
    }

    fun createRoutine() : Routine {
        val rutina = Routine (
            routineId = 1,
            userId = 1,
            name = "r1",
            pesoObjetivo = 60,
            diasEntrenamiento = "Lunes",
            ejercicios = "1"
        )
        val staticExercise = ExerciseModel(
            exerciseId = 1,
            name = "Static Exercise",
            muscle = "Biceps",
            equipment = "Dumbbell",
            difficulty = "beginner",
            type = "Strength",
            instructions = "Instructions for static exercise",
            isSelected = true,
            routineId = 1,
            isCompleted = false
        )

        return rutina
    }

    @Test
    fun testHU8_1() {

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
        appCompatEditText.perform(replaceText("prueba"), closeSoftKeyboard())

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
        appCompatEditText2.perform(replaceText("prueba@gmi.com"), closeSoftKeyboard())

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
        appCompatEditText3.perform(replaceText("prueba"), closeSoftKeyboard())

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
        appCompatEditText4.perform(replaceText("prueba"), closeSoftKeyboard())

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

        val textView = onView(
            allOf(
                withId(R.id.textMisRutinas), withText("Mis Rutinas"),
                withParent(
                    allOf(
                        withId(R.id.drawerLayout),
                        withParent(withId(R.id.fragment_containerHome))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Mis Rutinas")))

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

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.editTextText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_containerHome),
                        0
                    ),
                    11
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(replaceText("r1"), closeSoftKeyboard())

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.editTextNumber),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_containerHome),
                        0
                    ),
                    12
                ),
                isDisplayed()
            )
        )
        appCompatEditText6.perform(replaceText("60"), closeSoftKeyboard())

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

        val staticExerciseList = getStaticExerciseList()
        val rutina = createRoutine()


        val app = onData(anything())
            .inAdapterView(
                childAtPosition(
                    withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                    0
                )
            )
            .atPosition(0)
            app.perform(click())


        val selectedExercise = staticExerciseList[0]

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

        val button = onView(
            allOf(
                withId(R.id.btnRutina), withText("r1"),
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val materialButton7 = onView(
            allOf(
                withId(R.id.btnRutina), withText("r1"),
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


        val button2 = onView(
            allOf(
                withId(R.id.btnEliminarRutina), withText("Eliminar Rutina"),
                withParent(
                    allOf(
                        withId(R.id.linearLayoutButtons),
                        withParent(IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

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
}
