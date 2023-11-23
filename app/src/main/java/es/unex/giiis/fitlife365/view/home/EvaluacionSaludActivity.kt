package es.unex.giiis.fitlife365.view.home

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.database.FitLife365Database
import es.unex.giiis.fitlife365.model.User
import kotlinx.coroutines.launch

class EvaluacionSaludActivity : AppCompatActivity() {

    private lateinit var editTextEdad: EditText
    private lateinit var editTextEstatura: EditText
    private lateinit var editTextPeso: EditText
    private lateinit var spinnerSexo: Spinner
    private lateinit var btnAceptar: Button
    private lateinit var imageViewResultado: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_evaluation)

        // Obtener la fuente seleccionada desde SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val selectedFont = sharedPreferences.getString("font_preference", "openSans") // Valor predeterminado

        // Aplicar la fuente seleccionada
        if (selectedFont != null) {
            applyFont(window.decorView, selectedFont)
        }

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

        val user2 = intent?.getSerializableExtra("LOGIN_USER") as? User

        if (user2 != null) {
            cargarInformacionUsuario(user2)
        }


        btnAceptar.setOnClickListener {
            if (camposValidos()) {
                calcularPorcentajeSalud()
            } else {
                mostrarMensaje("Por favor, complete todos los campos.")
            }
        }

        imageViewResultado.setOnClickListener {
            val user = intent?.getSerializableExtra("LOGIN_USER") as? User
            if (user != null) {
                Log.d("EvaluacionSalud", "Nombre de usuario: ${user.name}")

                // Resto del código...
            } else {
                Log.d("EvaluacionSalud", "El usuario es nulo.")
            }

            // Abre la pantalla HomeActivity al hacer clic en la imagen
            val intent = Intent(this, HomeActivity::class.java).apply {
                // Pasa el usuario como parte de los datos del intent
                putExtra(HomeActivity.LOGIN_USER, user)
            }
            startActivity(intent)
        }
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
                    val typeface = resources.getFont(fontResId)

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

    private fun camposValidos(): Boolean {
        val edad = editTextEdad.text.toString().toFloatOrNull()
        val estatura = editTextEstatura.text.toString().toFloatOrNull()
        val peso = editTextPeso.text.toString().toFloatOrNull()

        return edad != null && estatura != null && peso != null
    }

    private fun guardarInformacionUsuario(edad: Float, estatura: Float, peso: Float, sexo: String) {
        val user = intent?.getSerializableExtra("LOGIN_USER") as? User
        if (user != null) {
            user.edad = edad.toInt()
            user.altura = estatura.toInt()
            user.peso = peso.toInt()
            user.sexo = sexo

            val database = FitLife365Database.getInstance(this)
            val userDao = database?.userDao()
            lifecycleScope.launch {
                userDao?.update(user.sexo, user.edad, user.altura, user.peso, user.userId)
            }
            Log.d("EvaluacionSalud", "Nombre de usuario: ${user.name}")
        } else {
            Log.d("EvaluacionSalud", "El usuario es nulo.")
        }
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
        val edad = editTextEdad.text.toString().toFloat()
        val estatura = editTextEstatura.text.toString().toFloat()
        val peso = editTextPeso.text.toString().toFloat()
        val sexo = spinnerSexo.selectedItem.toString()

        guardarInformacionUsuario(edad, estatura, peso, sexo)

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
