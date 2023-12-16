package es.unex.giiis.fitlife365.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
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
import es.unex.giiis.fitlife365.utils.FontUtils
import kotlinx.coroutines.launch


class MisRutinasFragment : Fragment() {
    private val viewModel: MisRutinasViewModel by viewModels { MisRutinasViewModel.Factory }

    private lateinit var recyclerView: RecyclerView
    private lateinit var rutinasAdapter: RoutineAdapter
    private var rutinasList: List<Routine> = mutableListOf()
    private lateinit var addRoutineButton : Button
    private lateinit var user: User

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_myroutines, container, false)

        user = arguments?.getSerializable(LOGIN_USER) as User
        addRoutineButton = view.findViewById(R.id.btnAddRoutine)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            FontUtils.applyFont(requireContext(), view, selectedFont)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        val textEmptyRecyclerView: TextView = view.findViewById(R.id.textEmptyRecyclerView)

        viewModel.getRoutinesByUser(user.userId)

        viewModel.rutinasList.observe(viewLifecycleOwner) { routinesList ->
            rutinasAdapter.actualizarListaRutinas(routinesList)

            // Actualiza la visibilidad del TextView según la cantidad de elementos en el RecyclerView
            textEmptyRecyclerView.visibility = viewModel.textEmptyVisibility.value ?: View.GONE
        }
        setUpListeners()
        subscribeUi(rutinasAdapter)
    }

    private fun setUpListeners(){
        addRoutineButton.setOnClickListener {
            val crearRutinaFragment = CrearRutinaFragment()
            crearRutinaFragment.setUser(arguments?.getSerializable(LOGIN_USER) as User)

            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_containerHome, crearRutinaFragment)
                ?.addToBackStack(null) // Agrega el fragmento actual al back stack
                ?.commit()
        }
    }

    private fun setUpRecyclerView() {
        recyclerView = requireView().findViewById(R.id.recyclerView)
        rutinasAdapter = RoutineAdapter(rutinasList) { rutina -> verDetallesRutina(rutina) }
        recyclerView.adapter = rutinasAdapter  // Asigna el adaptador al RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeUi(adapter: RoutineAdapter) {
        viewModel.toast.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            adapter.actualizarListaRutinas(rutinasList)
        }
    }


    private fun verDetallesRutina(rutina: Routine) {
        val intent = Intent(requireContext(), DetallesRutinaActivity::class.java)
        intent.putExtra("RUTINA", rutina)
        intent.putExtra("USER", arguments?.getSerializable(LOGIN_USER) as User)
        startActivity(intent)
    }
}