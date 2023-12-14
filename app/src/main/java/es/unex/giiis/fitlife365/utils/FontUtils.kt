package es.unex.giiis.fitlife365.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import es.unex.giiis.fitlife365.R

object FontUtils {
    fun applyFont(context: Context, view: View, fontName: String) {
        when (view) {
            is ViewGroup -> {
                for (i in 0 until view.childCount) {
                    applyFont(context, view.getChildAt(i), fontName)
                }
            }
            is TextView -> {
                try {
                    val fontResId = getFontResourceId(fontName)
                    val typeface = ResourcesCompat.getFont(context, fontResId)
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is EditText -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = getFontResourceId(fontName)

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = ResourcesCompat.getFont(context, fontResId)

                    // Aplicar la fuente a la barra de edición de texto
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is Button -> {
                try {
                    // Obtener el identificador del recurso de fuente
                    val fontResId = getFontResourceId(fontName)

                    // Crear el objeto Typeface con la fuente seleccionada
                    val typeface = ResourcesCompat.getFont(context, fontResId)

                    // Aplicar la fuente al botón
                    view.typeface = typeface

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getFontResourceId(fontName: String): Int {
        return when (fontName) {
            "openSans" -> R.font.opensans
            "Roboto" -> R.font.roboto
            "Ubuntu" -> R.font.ubuntu
            "Ephesis" -> R.font.ephesis
            else -> R.font.opensans
        }
    }
}
