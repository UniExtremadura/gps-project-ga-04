package es.unex.giiis.fitlife365.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.User


class MisRutinasFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var usernameText: TextView
    private lateinit var buttonContinuar: Button
    private lateinit var buttonCrearRutina: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var imageView4: ImageView
    private lateinit var imageView5: ImageView
    private lateinit var imageView6: ImageView
    private lateinit var imageView7: ImageView
    private var param1: String? = null
    private var param2: String? = null
    companion object {
        const val LOGIN_USER = "LOGIN_USER"

        fun newInstance(user: User): MisRutinasFragment {
            val fragment = MisRutinasFragment()
            val bundle = Bundle()
            bundle.putSerializable(LOGIN_USER, user)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_myroutines, container, false)

        return view
    }
    
    private fun eliminarRutina(button: Button) {
        // Oculta el botón
        button.visibility = View.GONE
        // Luego, elimina la rutina de tu lista de rutinas
    }

}
