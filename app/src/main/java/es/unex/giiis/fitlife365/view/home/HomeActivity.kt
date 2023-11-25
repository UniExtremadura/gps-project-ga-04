package es.unex.giiis.fitlife365.view.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.User

class HomeActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_home)
    }
}