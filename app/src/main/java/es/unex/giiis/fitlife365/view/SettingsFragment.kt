package es.unex.giiis.fitlife365.view

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.preference.PreferenceFragmentCompat
import es.unex.giiis.fitlife365.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)


    }
}