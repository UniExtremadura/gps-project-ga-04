package es.unex.giiis.fitlife365.view

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import es.unex.giiis.fitlife365.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val radioButton: RadioButton = findViewById(R.id.radioButton)
        val radioButton2: RadioButton = findViewById(R.id.radioButton2)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton -> {
                    // Cambiar el tema a claro
                    radioButton.isChecked = true
                    radioButton2.isChecked = false
                }
                R.id.radioButton2 -> {
                    // Cambiar el tema a oscuro
                    radioButton.isChecked = false
                    radioButton2.isChecked = true
                }
            }
        }
    }
}

