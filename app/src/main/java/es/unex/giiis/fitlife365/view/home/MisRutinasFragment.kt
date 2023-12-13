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
import es.unex.giiis.fitlife365.FitLife365Application
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.api.getNetworkService
import es.unex.giiis.fitlife365.data.Repository
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
    private lateinit var repository: Repository


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

        val appContainer = (this.activity?.application as FitLife365Application).appContainer
        repository = appContainer.repository

        val user = arguments?.getSerializable(LOGIN_USER) as User

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            applyFont(view, selectedFont)
        }

        recyclerView = view.findViewById(R.id.recyclerView)
        rutinasAdapter = RoutineAdapter(rutinasList) { rutina -> verDetallesRutina(rutina) }
        recyclerView.adapter = rutinasAdapter  // Asigna el adaptador al RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val textEmptyRecyclerView: TextView = view.findViewById(R.id.textEmptyRecyclerView)

        lifecycleScope.launch {
            rutinasList = repository.getRoutinesByUser(user.userId) ?: emptyList()
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
                    val typeface = ResourcesCompat.getFont(requireContext(), fontResId)

                    // Aplicar la fuente
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is EditText -> {
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
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente a la barra de edición de texto
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is Button -> {
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
                    val typeface = resources.getFont(fontResId)

                    // Aplicar la fuente al botón
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
