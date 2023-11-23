package es.unex.giiis.fitlife365.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
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

        // Obtener la fuente seleccionada desde SharedPreferences
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            applyFont(window.decorView, selectedFont)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.settingsContainer, SettingsFragment())
            .commit()
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
                    val typeface = ResourcesCompat.getFont(view.context, fontResId)

                    // Aplicar la fuente
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}


