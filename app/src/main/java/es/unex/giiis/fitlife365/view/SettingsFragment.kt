package es.unex.giiis.fitlife365.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
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
        themeSwitchPreference?.setOnPreferenceChangeListener { _, newValue ->
            val sharedPreferences = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }
            if (sharedPreferences != null) {
                sharedPreferences.edit().putBoolean("theme_preference", newValue as Boolean).apply()
            }

            // Mostrar un Toast indicando que la aplicación necesita reiniciarse
            Toast.makeText(context, "Reinicie la aplicación para aplicar los cambios de tema", Toast.LENGTH_SHORT).show()

            true
        }

    }
}
