package es.uniovi.converter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Ratio fijo de momento (1 EUR = 1.16 USD). No puede ser nulo y se inicializa aquí mismo
    private var euroToDollar: Double = 1.16

    // Referencias a las cajas de texto. lateinit = "las inicializo luego, en onCreate",
    // porque findViewById solo funciona cuando la pantalla ya está creada
    private lateinit var editTextEuros: EditText
    private lateinit var editTextDollars: EditText

    // Se ejecuta al crearse la pantalla
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Carga el XML activity_main.xml como interfaz de esta pantalla
        setContentView(R.layout.activity_main)
        // Ajusta los márgenes para no tapar la barra de estado (código de la plantilla)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Enlazamos las variables con las cajas del XML mediante su id
        editTextEuros = findViewById(R.id.editTextEuros)
        editTextDollars = findViewById(R.id.editTextDollars)
    }

    // Lo llama el botón "To Dollars" (android:onClick="onClickToDollars")
    fun onClickToDollars(view: View) {
        // Mensaje temporal y log para comprobar que el botón responde
        Toast.makeText(this, "¡Me han pulsado! (a dólares)", Toast.LENGTH_SHORT).show()
        Log.d("MainActivity", "Pulsado botón a dólares")
        // Euros -> dólares: se multiplica por el ratio
        convert(editTextEuros, editTextDollars, euroToDollar)
    }

    // Lo llama el botón "To Euros"
    fun onClickToEuros(view: View) {
        Toast.makeText(this, "¡Me han pulsado! (a euros)", Toast.LENGTH_SHORT).show()
        Log.d("MainActivity", "Pulsado botón a euros")
        // Dólares -> euros: se divide por el ratio, es decir, se multiplica por 1/ratio
        convert(editTextDollars, editTextEuros, 1 / euroToDollar)
    }

    // Lee 'source', convierte y escribe el resultado en 'destination'
    private fun convert(source: EditText, destination: EditText, factor: Double) {
        // Texto de la caja origen
        val text = source.text.toString()
        // toDoubleOrNull devuelve null si no es un número válido (p. ej. caja vacía)
        val value = text.toDoubleOrNull()
        if (value == null) {
            // Si no hay número válido, vaciamos el destino y salimos
            destination.setText("")
            return
        }
        // Calculamos y mostramos el resultado
        destination.setText((value * factor).toString())
    }
}