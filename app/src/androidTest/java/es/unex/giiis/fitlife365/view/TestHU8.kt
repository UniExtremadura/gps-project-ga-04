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

    @Test
    fun testHU8() {
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
        appCompatEditText2.perform(replaceText("prueba@gmail.com"), closeSoftKeyboard())

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
        appCompatEditText5.perform(replaceText("rutina 1"), closeSoftKeyboard())

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
        appCompatEditText6.perform(replaceText("80"), closeSoftKeyboard())

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
            .atPosition(5)
        appCompatCheckedTextView.perform(click())

        val button = onView(
            allOf(
                withId(R.id.btnGuardarEnRutina), withText("Guardar en Rutina"),
                withParent(withParent(withId(R.id.fragment_containerHome))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

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

        val button2 = onView(
            allOf(
                withId(R.id.btnRutina), withText("rutina 1"),
                withParent(withParent(withId(R.id.recyclerView))),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val materialButton7 = onView(
            allOf(
                withId(R.id.btnRutina), withText("rutina 1"),
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

        val button3 = onView(
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
        button3.check(matches(isDisplayed()))
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
