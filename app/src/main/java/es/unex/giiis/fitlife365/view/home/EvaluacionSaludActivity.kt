package es.unex.giiis.fitlife365.view.home

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.User
import es.unex.giiis.fitlife365.utils.FontUtils
import es.unex.giiis.fitlife365.view.viewmodels.EvaluacionSaludViewModel
import es.unex.giiis.fitlife365.view.viewmodels.HomeViewModel

class EvaluacionSaludActivity : AppCompatActivity() {

    private lateinit var editTextEdad: EditText
    private lateinit var editTextEstatura: EditText
    private lateinit var editTextPeso: EditText
    private lateinit var spinnerSexo: Spinner
    private lateinit var btnAceptar: Button
    private lateinit var imageViewResultado: ImageView
    private lateinit var usuario: User

    //private lateinit var repository: Repository
    private val viewModel: EvaluacionSaludViewModel by viewModels { EvaluacionSaludViewModel.Factory }
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluacion_salud)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            FontUtils.applyFont(this, window.decorView, selectedFont)
        }

        homeViewModel.user.observe(this) { user ->
            viewModel.user = user
        }
        usuario = intent?.getSerializableExtra("LOGIN_USER") as User

        Toast.makeText(
            this,
            "Verifique su sexo, edad, altura y peso",
            Toast.LENGTH_LONG
        ).show()

        editTextEdad = findViewById(R.id.editTextEdad)
        editTextEstatura = findViewById(R.id.editTextEstatura)
        editTextPeso = findViewById(R.id.editTextPeso)
        spinnerSexo = findViewById(R.id.spinnerSexo)
        btnAceptar = findViewById(R.id.btnAceptar)
        imageViewResultado = findViewById(R.id.imageViewResultado)


        // Configura el adapter para el spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_sexo,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSexo.adapter = adapter

        // Configura el tipo de entrada del teclado para los campos de edad, estatura y peso
        editTextEdad.inputType = InputType.TYPE_CLASS_NUMBER
        editTextEstatura.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        editTextPeso.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        cargarInformacionUsuario(usuario)

        setUpListeners()
    }

    private fun setUpListeners(){
        btnAceptar.setOnClickListener {
            if (camposValidos()) {
                calcularPorcentajeSalud()
            } else {
                mostrarMensaje("Por favor, complete todos los campos.")
            }
        }

        imageViewResultado.setOnClickListener {
            Log.d("EvaluacionSalud", "Nombre de usuario: ${usuario.name}")

            // Abre la pantalla HomeActivity al hacer clic en la imagen
            val intent = Intent(this, HomeActivity::class.java).apply {
                // Pasa el usuario como parte de los datos del intent
                putExtra("LOGIN_USER", usuario)
            }
            startActivity(intent)
        }
    }

    private fun camposValidos(): Boolean {
        val edad = editTextEdad.text.toString().toFloatOrNull()
        val estatura = editTextEstatura.text.toString().toFloatOrNull()
        val peso = editTextPeso.text.toString().toFloatOrNull()

        return edad != null && estatura != null && peso != null
    }


    private fun cargarInformacionUsuario(user: User) {
        val sexoArray = resources.getStringArray(R.array.opciones_sexo)
        val sexoIndex = sexoArray.indexOf(user.sexo)

        // Rellenar los campos con los datos del usuario
        editTextPeso.setText(user.peso.toString())
        editTextEstatura.setText(user.altura.toString())
        editTextEdad.setText(user.edad.toString())
        spinnerSexo.setSelection(sexoIndex)
    }

    private fun calcularPorcentajeSalud() {
        viewModel.user = intent?.getSerializableExtra("LOGIN_USER") as? User
        viewModel.estatura = editTextEstatura.text.toString().toInt()
        viewModel.peso = editTextPeso.text.toString().toInt()
        viewModel.sexo = spinnerSexo.selectedItem.toString()
        viewModel.userID = viewModel.user?.userId ?: 0

        val peso = editTextPeso.text.toString().toFloat()
        val estatura = editTextEstatura.text.toString().toFloat()

        viewModel.guardarInformacionUsuario()

        // Lógica para calcular el porcentaje de salud
        val porcentajeSalud = calcularPorcentajeSalud(peso, estatura)

        // Mostrar el porcentaje de salud en un Toast o en un TextView, según tus necesidades
        mostrarMensaje("Porcentaje de salud: $porcentajeSalud%")
        mostrarMensaje("Pulse en la foto para ir a la pantalla principal")
        mostrarImagenResultado(porcentajeSalud)
    }

    private fun calcularPorcentajeSalud(peso: Float, estaturaCm: Float): Int {
        // Convertir la estatura a metros
        val estaturaMetros = estaturaCm / 100

        // Calcular el IMC
        val imc = peso / (estaturaMetros * estaturaMetros)

        return when {
            imc < 18.5 -> 5 // Bajo peso
            imc < 24.9 -> 50 // Peso normal
            imc < 29.9 -> 85 // Sobrepeso
            else -> 95 // Obesidad
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarImagenResultado(porcentajeSalud: Int) {
        val imagenResource = when {
            porcentajeSalud <= 5 -> R.drawable.bajo
            porcentajeSalud <= 50 -> R.drawable.normal
            porcentajeSalud <= 85 -> R.drawable.sobrepeso
            else -> R.drawable.obeso
        }

        imageViewResultado.setImageResource(imagenResource)
        imageViewResultado.visibility = View.VISIBLE
    }
}