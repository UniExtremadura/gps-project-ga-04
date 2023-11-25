package es.unex.giiis.fitlife365.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import kotlinx.coroutines.launch


class MisRutinasFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var rutinasAdapter: RoutineAdapter
    private var rutinasList: List<Routine> = mutableListOf()

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
        val database = FitLife365Database.getInstance(requireContext())
        val rutinaDao = database?.routineDao()
        val user = arguments?.getSerializable(LOGIN_USER) as User

        rutinasAdapter = RoutineAdapter(rutinasList) { rutina -> verDetallesRutina(rutina) }
        recyclerView.adapter = rutinasAdapter  // Asigna el adaptador al RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val textEmptyRecyclerView: TextView = view.findViewById(R.id.textEmptyRecyclerView)

        lifecycleScope.launch {
            rutinasList = rutinaDao?.getRoutinesByUser(user.userId) ?: emptyList()
            rutinasAdapter.actualizarListaRutinas(rutinasList)

            // Actualiza la visibilidad del TextView según la cantidad de elementos en el RecyclerView
            textEmptyRecyclerView.visibility = if (rutinasList.isEmpty()) View.VISIBLE else View.GONE
        }

        return view
    }



    private fun verDetallesRutina(rutina: Routine) {
        val intent = Intent(requireContext(), DetallesRutinaActivity::class.java)
        intent.putExtra("RUTINA", rutina)
        intent.putExtra("USER", arguments?.getSerializable(LOGIN_USER) as User)
        startActivity(intent)
    }
}
