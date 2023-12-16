package es.unex.giiis.fitlife365

import EditarPerfilViewModel
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.MainActivity
import es.unex.giiis.fitlife365.view.home.EvaluacionSaludActivity
import es.unex.giiis.fitlife365.utils.FontUtils
import es.unex.giiis.fitlife365.view.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EditarPerfilFragment : Fragment() {
    private lateinit var editTextNombre: EditText
    private lateinit var spinnerSexo: Spinner
    private lateinit var editTextEdad: EditText
    private lateinit var editTextAltura: EditText
    private lateinit var editTextPeso: EditText
    private lateinit var btnAceptar: Button
    private lateinit var btnEliminar: Button
    private val viewModel: EditarPerfilViewModel by viewModels { EditarPerfilViewModel.Factory }
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_perfil, container, false)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedFont =
            sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            FontUtils.applyFont(requireContext(), view, selectedFont)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sexo = listOf("Hombre", "Mujer", "Otro")

        // Obtener referencias a las vistas
        editTextNombre = view.findViewById(R.id.et_nombre)
        spinnerSexo = view.findViewById(R.id.spinnerMaterial)
        editTextEdad = view.findViewById(R.id.et_edad)
        editTextAltura = view.findViewById(R.id.et_estatura)
        editTextPeso = view.findViewById(R.id.et_peso)
        btnAceptar = view.findViewById(R.id.btnAceptar)
        btnEliminar = view.findViewById<Button>(R.id.buttonEliminar)

        val adapterSexo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexo)
        adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSexo.adapter = adapterSexo

        var usuario: User? = null  // Cambia a User?

        // Obtener el usuario del Bundle
        homeViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.user = user
            user?.let { nonNullUser ->
                usuario = nonNullUser
                actualizarInterfazUsuario(usuario)
            }
        }
    }

    private fun setUpListeners(user : User){
        btnAceptar.setOnClickListener {
            if (user != null) {
                // Mostrar un cuadro de diálogo de confirmación
                mostrarDialogoConfirmacion(user) { confirmed ->
                    if (confirmed) {
                        // El usuario ha confirmado, ejecutar el módulo actualizarUsuario
                        viewModel.actualizarUsuario(user)
                        // Ir a la pantalla de MainActivity
                        val nombre = editTextNombre.text.toString()
                        val sexo = spinnerSexo.selectedItem.toString()
                        val edad = editTextEdad.text.toString().toIntOrNull() ?: 0
                        val altura = editTextAltura.text.toString().toIntOrNull() ?: 0
                        val peso = editTextPeso.text.toString().toIntOrNull() ?: 0

                        val intent = Intent(requireContext(), EvaluacionSaludActivity::class.java).apply {
                            putExtra("LOGIN_USER", user.copy(name = nombre, sexo = sexo, edad = edad, altura = altura, peso = peso))
                        }
                        startActivity(intent)

                        val nuevoNombreUsuario = editTextNombre.text.toString()
                        val nombreUsuario = requireView().findViewById<TextView>(R.id.usernameText)
                        // Actualizar el nombre de usuario en el TextView
                        nombreUsuario.text = nuevoNombreUsuario
                    }
                }
            }
        }

        btnEliminar.setOnClickListener {
            if (user != null) {
                mostrarDialogoConfirmacionEliminacion(user) { confirmed ->
                    if (confirmed) {
                        viewModel.eliminarUsuario(user)
                        // Ir a la pantalla de MainActivity
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
            }
        }
    }
    private fun actualizarInterfazUsuario(usuario: User?) {
        val sexo = listOf("Hombre", "Mujer", "Otro")
        usuario?.let {
            editTextNombre.setText(it.name)
            editTextEdad.setText(it.edad.toString())
            val sexoIndex = sexo.indexOf(it.sexo)
            spinnerSexo.setSelection(if (sexoIndex != -1) sexoIndex else 0)
            editTextAltura.setText(it.altura.toString())
            editTextPeso.setText(it.peso.toString())
            setUpListeners(it)
        }
    }

    private fun mostrarDialogoConfirmacion(user: User, callback: (Boolean) -> Unit) { //tercera subtarea
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de actualizar tu perfil?")

        // Configurar el botón de aceptar
        builder.setPositiveButton("Aceptar") { _, _ ->
            // El usuario ha confirmado, ejecutar el módulo actualizarUsuario
            viewModel.actualizarUsuario(user)
            // Ir a la pantalla de MainActivity

            val nombre = editTextNombre.text.toString()
            val sexo = spinnerSexo.selectedItem.toString()
            val edad = editTextEdad.text.toString().toIntOrNull() ?: 0
            val altura = editTextAltura.text.toString().toIntOrNull() ?: 0
            val peso = editTextPeso.text.toString().toIntOrNull() ?: 0

            val intent = Intent(requireContext(), EvaluacionSaludActivity::class.java).apply {
                putExtra("LOGIN_USER", user.copy(name = nombre, sexo = sexo, edad = edad, altura = altura, peso = peso))
            }
            startActivity(intent)
            // Mostrar un mensaje Toast después de la actualización
            callback(true)
        }

        // Configurar el botón de cancelar
        builder.setNegativeButton("Cancelar") { _, _ ->
            // El usuario ha cancelado, no hacer nada
            callback(false)
        }

        // Mostrar el cuadro de diálogo
        builder.show()
    }

    private fun mostrarDialogoConfirmacionEliminacion(user: User, callback: (Boolean) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de eliminar tu perfil?")

        // Configurar el botón de aceptar
        builder.setPositiveButton("Aceptar") { _, _ ->
            // El usuario ha confirmado, ejecutar el módulo eliminarUsuario
            callback(true)
        }

        // Configurar el botón de cancelar
        builder.setNegativeButton("Cancelar") { _, _ ->
            // El usuario ha cancelado, no hacer nada
            callback(false)
        }

        // Mostrar el cuadro de diálogo
        builder.show()
    }
}