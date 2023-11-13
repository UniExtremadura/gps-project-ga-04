package es.unex.giiis.fitlife365.view.home

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.SettingsActivity

class CrearRutinaActivity : AppCompatActivity() {

    private lateinit var usernameText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.routine_toolbar)

        val user = intent.getSerializableExtra(MisRutinasActivity.LOGIN_USER) as User
        usernameText = findViewById(R.id.usernameText2)
        usernameText.text = user.name

        usernameText.setOnClickListener() {
            navigateToSettings()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.settingsContainer, CrearRutinaFragment())
            .commit()
    }

    private fun navigateToSettings(){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}
