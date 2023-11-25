package es.unex.giiis.fitlife365.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import es.unex.giiis.fitlife365.R
import es.unex.giiis.fitlife365.model.User

class EvaluacionSaludActivity : AppCompatActivity() {

    private lateinit var editTextEdad: EditText
    private lateinit var editTextEstatura: EditText
    private lateinit var editTextPeso: EditText
    private lateinit var spinnerSexo: Spinner
    private lateinit var btnAceptar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluacion_salud)

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
        editTextEstatura.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        editTextPeso.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

    }

    }