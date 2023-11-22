package es.unex.giiis.fitlife365.view

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.fitlife365.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePreference = sharedPreferences.getBoolean("theme_preference", false)

        if (themePreference) {
            // Aplicar tema oscuro
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            // Aplicar tema claro
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        supportFragmentManager.beginTransaction()
            .replace(R.id.settingsContainer, SettingsFragment())
            .commit()
    }
}


