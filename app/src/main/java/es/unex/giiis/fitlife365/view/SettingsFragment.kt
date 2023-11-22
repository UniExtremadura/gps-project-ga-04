package es.unex.giiis.fitlife365.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
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
}


