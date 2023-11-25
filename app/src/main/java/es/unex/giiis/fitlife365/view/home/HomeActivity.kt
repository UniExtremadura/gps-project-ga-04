package es.unex.giiis.fitlife365.view.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.databinding.ActivityHomeBinding
import es.unex.giiis.fitlife365.model.User

class HomeActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var imageViewEvS: ImageView
    private lateinit var usernameTextEvS: TextView
    private lateinit var binding: ActivityHomeBinding


    companion object {
        const val LOGIN_USER = "LOGIN_USER"

        fun start(context: Context, user: User) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(LOGIN_USER, user)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)

        toolbar = findViewById(R.id.toolbar3)
        imageViewEvS = findViewById(R.id.imageViewEvS)
        usernameTextEvS = findViewById(R.id.usernameTextEvS)

        setUpListeners()

    }


    private fun setUpListeners() {
        with(binding){

            imageViewEvS.setOnClickListener {
                navigateToEvaluacionSalud()
            }
            usernameTextEvS.setOnClickListener {
                navigateToEvaluacionSalud()
            }
        }
    }
    private fun navigateToEvaluacionSalud() {
        val user = intent.getSerializableExtra(LOGIN_USER) as? User
        val intent = Intent(this, EvaluacionSaludActivity::class.java).apply {
            // Pasa el usuario como parte de los datos del intent
            putExtra("LOGIN_USER", user)
        }
        startActivity(intent)
    }
}