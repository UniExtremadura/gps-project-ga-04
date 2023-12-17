package es.unex.giiis.fitlife365.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.Routine
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.utils.FontUtils
import es.unex.giiis.fitlife365.view.adapters.RoutineAdapter
import es.unex.giiis.fitlife365.view.viewmodels.HomeViewModel
import es.unex.giiis.fitlife365.view.viewmodels.MisRutinasViewModel


class MisRutinasFragment : Fragment() {
    private val viewModel: MisRutinasViewModel by viewModels { MisRutinasViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var rutinasAdapter: RoutineAdapter
    private var rutinasList: List<Routine> = mutableListOf()
    private lateinit var addRoutineButton : Button
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_myroutines, container, false)

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
        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user

            user?.let { nonNullUser ->
                this.user = nonNullUser

                viewModel.getRoutinesByUser()
            }
        }

        val textEmptyRecyclerView: TextView = view.findViewById(R.id.textEmptyRecyclerView)

        viewModel.rutinasList.observe(viewLifecycleOwner) { routinesList ->
            rutinasAdapter.actualizarListaRutinas(routinesList)

            // Actualiza la visibilidad del TextView segÃºn la cantidad de elementos en el RecyclerView
            textEmptyRecyclerView.visibility = viewModel.textEmptyVisibility.value ?: View.GONE
        }
        setUpListeners()
        subscribeUi(rutinasAdapter)
    }

    private fun setUpListeners(){
        addRoutineButton.setOnClickListener {
            val crearRutinaFragment = CrearRutinaFragment()
            crearRutinaFragment.setUser(viewModel.user!!)

            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_containerHome, crearRutinaFragment)
                ?.addToBackStack(null) // Agrega el fragmento actual al back stack
                ?.commit()
        }
    }

    private fun setUpRecyclerView() {
        recyclerView = requireView().findViewById(R.id.recyclerView)
        rutinasAdapter = RoutineAdapter(rutinasList) {
            homeViewModel.onShowClick(it)
        }
        recyclerView.adapter = rutinasAdapter  // Asigna el adaptador al RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeUi(adapter: RoutineAdapter) {
        viewModel.toast.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            adapter.actualizarListaRutinas(rutinasList)
        }
    }
}