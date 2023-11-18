package es.unex.giiis.fitlife365.view.home

import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.unex.giiis.fitlife365.R

class EvaluacionSalud : AppCompatActivity() {

    private lateinit var editTextEdad: EditText
    private lateinit var editTextEstatura: EditText
    private lateinit var editTextPeso: EditText
    private lateinit var spinnerSexo: Spinner
    private lateinit var btnAceptar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.health_evaluation)

        editTextEdad = findViewById(R.id.editTextEdad)
        editTextEstatura = findViewById(R.id.editTextEstatura)
        editTextPeso = findViewById(R.id.editTextPeso)
        spinnerSexo = findViewById(R.id.spinnerSexo)
        btnAceptar = findViewById(R.id.btnAceptar)

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

        btnAceptar.setOnClickListener {
            if (camposValidos()) {
                calcularPorcentajeSalud()
            } else {
                mostrarMensaje("Por favor, complete todos los campos.")
            }
        }
    }

    private fun camposValidos(): Boolean {
        val edad = editTextEdad.text.toString().toFloatOrNull()
        val estatura = editTextEstatura.text.toString().toFloatOrNull()
        val peso = editTextPeso.text.toString().toFloatOrNull()

        return edad != null && estatura != null && peso != null
    }

    private fun calcularPorcentajeSalud() {
        val edad = editTextEdad.text.toString().toFloat()
        val estatura = editTextEstatura.text.toString().toFloat()
        val peso = editTextPeso.text.toString().toFloat()
        val sexo = spinnerSexo.selectedItem.toString()

        // Lógica para calcular el porcentaje de salud
        val porcentajeSalud = calcularPorcentajeSalud(peso, estatura)

        // Mostrar el porcentaje de salud en un Toast o en un TextView, según tus necesidades
        mostrarMensaje("Porcentaje de salud: $porcentajeSalud%")
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
}
