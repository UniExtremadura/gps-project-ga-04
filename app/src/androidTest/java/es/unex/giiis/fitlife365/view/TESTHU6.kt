package es.unex.giiis.fitlife365.view


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import es.unex.giiis.fitlife365.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TESTHU6 {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun tESTHU6() {
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
        appCompatEditText.perform(replaceText("admin"), closeSoftKeyboard())

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
        appCompatEditText2.perform(replaceText("admin@gmail.com"), closeSoftKeyboard())

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
        appCompatEditText3.perform(replaceText("admin"), closeSoftKeyboard())

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
        appCompatEditText4.perform(replaceText("admin"), closeSoftKeyboard())

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

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.editTextEdad), withText("0"),
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
        appCompatEditText5.perform(replaceText("20"))

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.editTextEdad), withText("20"),
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
        appCompatEditText6.perform(closeSoftKeyboard())

        val appCompatEditText7 = onView(
            allOf(
                withId(R.id.editTextEstatura), withText("0"),
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
        appCompatEditText7.perform(replaceText("180"))

        val appCompatEditText8 = onView(
            allOf(
                withId(R.id.editTextEstatura), withText("180"),
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
        appCompatEditText8.perform(closeSoftKeyboard())

        val appCompatEditText9 = onView(
            allOf(
                withId(R.id.editTextPeso), withText("0"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText9.perform(replaceText("70"))

        val appCompatEditText10 = onView(
            allOf(
                withId(R.id.editTextPeso), withText("70"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        appCompatEditText10.perform(closeSoftKeyboard())

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

        val materialTextView = onView(
            allOf(
                withId(R.id.usernameText), withText("admin"),
                childAtPosition(
                    allOf(
                        withId(R.id.drawerLayout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textView = onView(
            allOf(
                withId(android.R.id.summary), withText("Not set"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Not set")))

        val recyclerView = onView(
            allOf(
                withId(androidx.preference.R.id.recycler_view),
                childAtPosition(
                    withId(android.R.id.list_container),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(7, click()))

        val appCompatCheckedTextView = onData(anything())
            .inAdapterView(
                allOf(
                    withId(androidx.appcompat.R.id.select_dialog_listview),
                    childAtPosition(
                        withId(androidx.appcompat.R.id.contentPanel),
                        0
                    )
                )
            )
            .atPosition(3)
        appCompatCheckedTextView.perform(click())

        pressBack()

        val materialTextView2 = onView(
            allOf(
                withId(R.id.usernameText), withText("admin"),
                childAtPosition(
                    allOf(
                        withId(R.id.drawerLayout),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

        val textView2 = onView(
            allOf(
                withId(android.R.id.summary), withText("Ephesis"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Ephesis")))
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
