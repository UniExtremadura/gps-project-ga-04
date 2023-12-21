package es.unex.giiis.fitlife365.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import es.unex.giiis.fitlife365.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            view?.let { applyFont(it, selectedFont) }
        }
        val fontPreference = findPreference<ListPreference>("font_preference")
        fontPreference?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                // Mostrar un Toast solo cuando cambia la fuente
                Toast.makeText(
                    context,
                    "Reinicie la aplicación para aplicar los cambios de fuente",
                    Toast.LENGTH_SHORT
                ).show()

                true
            }
        }
        // Obtener la preferencia del tema
        val themeSwitchPreference = findPreference<SwitchPreference>("theme")
        themeSwitchPreference?.let {
            // Obtener el estado actual de la preferencia
            val currentThemeValue = it.isChecked

            // Actualizar el resumen según el estado actual
            val themeMessage = if (currentThemeValue) {
                "Modo Oscuro"
            } else {
                "Modo Claro"
            }
            it.summary = themeMessage

            // Establecer un oyente para la preferencia de cambio de tema
            it.setOnPreferenceChangeListener { _, newValue ->
                // Actualizar el resumen según el nuevo estado del interruptor
                val newThemeMessage = if (newValue as Boolean) {
                    "Modo Oscuro"
                } else {
                    "Modo Claro"
                }
                it.summary = newThemeMessage

                // Guardar el nuevo valor en SharedPreferences
                val sharedPreferences =
                    context?.let { context -> PreferenceManager.getDefaultSharedPreferences(context) }
                sharedPreferences?.edit()?.putBoolean("theme_preference", newValue)?.apply()

                // Mostrar un Toast indicando que la aplicación necesita reiniciarse
                Toast.makeText(
                    context,
                    "Reinicie la aplicación para aplicar los cambios de tema",
                    Toast.LENGTH_SHORT
                ).show()

                true
            }
        }
    }
    private fun applyFont(view: View, fontName: String) {
        when (view) {
            is ViewGroup -> {
                for (i in 0 until view.childCount) {
                    applyFont(view.getChildAt(i), fontName)
                }
            }
            is TextView -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = when (fontName) {
                        "openSans" -> R.font.opensans
                        "Roboto" -> R.font.roboto
                        "Ubuntu" -> R.font.ubuntu
                        "Ephesis" -> R.font.ephesis
                        else -> R.font.opensans // Valor predeterminado
                    }

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is EditText -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = when (fontName) {
                        "openSans" -> R.font.opensans
                        "Roboto" -> R.font.roboto
                        "Ubuntu" -> R.font.ubuntu
                        "Ephesis" -> R.font.ephesis
                        else -> R.font.opensans // Valor predeterminado
                    }

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente a la barra de edición de texto
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is Button -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = when (fontName) {
                        "openSans" -> R.font.opensans
                        "Roboto" -> R.font.roboto
                        "Ubuntu" -> R.font.ubuntu
                        "Ephesis" -> R.font.ephesis
                        else -> R.font.opensans // Valor predeterminado
                    }

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente al botón
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}


