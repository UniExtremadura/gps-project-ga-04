package es.unex.giiis.fitlife365

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
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.view.MainActivity
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.view.home.CrearRutinaFragment
import es.unex.giiis.fitlife365.view.home.EvaluacionSalud
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditarPerfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditarPerfilFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var editTextNombre: EditText
    private lateinit var spinnerSexo: Spinner
    private lateinit var editTextEdad: EditText
    private lateinit var editTextAltura: EditText
    private lateinit var editTextPeso: EditText
    private lateinit var btnAceptar: Button
    private lateinit var btnEliminar: Button

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
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_perfil, container, false)
        val sexo = listOf("Hombre", "Mujer", "Otro")

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            applyFont(view, selectedFont)
        }

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

        // Obtener el usuario del Bundle
        val user = arguments?.getSerializable("LOGIN_USER") as? User

        if (user != null) {
            editTextNombre.setText(user.name)
            editTextEdad.setText(user.edad.toString())
            val sexoIndex = sexo.indexOf(user.sexo)
            spinnerSexo.setSelection(if (sexoIndex != -1) sexoIndex else 0)
            editTextAltura.setText(user.altura.toString())
            editTextPeso.setText(user.peso.toString())
        }

        // Configurar el evento de clic para el botón Aceptar
        btnAceptar.setOnClickListener {
            if (user != null) {
                // Mostrar un cuadro de diálogo de confirmación
                mostrarDialogoConfirmacion(user) { confirmed ->
                    if (confirmed) {
                        // El usuario ha confirmado, ejecutar el módulo actualizarUsuario
                        actualizarUsuario(user)
                        val nuevoNombreUsuario = editTextNombre.text.toString()
                        val nombreUsuario = view.findViewById<TextView>(R.id.usernameText)
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
                        eliminarUsuario(user)
                    }
                }
            }
        }

        return view
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
        }
    }

    private fun mostrarDialogoConfirmacion(user: User, callback: (Boolean) -> Unit) { //tercera subtarea
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de actualizar tu perfil?")

        // Configurar el botón de aceptar
        builder.setPositiveButton("Aceptar") { _, _ ->
            // El usuario ha confirmado, ejecutar el módulo actualizarUsuario
            actualizarUsuario(user)
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

    private fun actualizarUsuario(user: User) {
        // Obtener los valores de las vistas
        val userId = user.userId
        val nombre = editTextNombre.text.toString()
        val sexo = spinnerSexo.selectedItem.toString()
        val edad = editTextEdad.text.toString().toIntOrNull() ?: 0
        val altura = editTextAltura.text.toString().toIntOrNull() ?: 0
        val peso = editTextPeso.text.toString().toIntOrNull() ?: 0

        // Llamar al método updateUser del UserDao
        GlobalScope.launch(Dispatchers.IO) {
            val userDao = FitLife365Database.getInstance(requireContext())?.userDao()
            if (userDao != null) {
                if (userId != null) {
                    userDao.updateUser(userId, nombre, sexo, edad, altura, peso)

                    val intent = Intent(requireContext(), EvaluacionSalud::class.java).apply {
                        putExtra("LOGIN_USER", user.copy(name = nombre, sexo = sexo, edad = edad, altura = altura, peso = peso))
                    }
                    startActivity(intent)
                    // Mostrar un mensaje Toast después de la actualización
                    GlobalScope.launch(Dispatchers.Main) {
                    }
                }
            }
        }
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
    private fun eliminarUsuario(user: User) {
        // Llamar al método deleteUser del UserDao
        GlobalScope.launch(Dispatchers.IO) {
            val userDao = FitLife365Database.getInstance(requireContext())?.userDao()
            if (userDao != null) {
                userDao.deleteUser(user)

                    // Ir a la pantalla de MainActivity
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EditarPerfilFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(user: User) =
            EditarPerfilFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("LOGIN_USER", user)
                }
            }
    }
}