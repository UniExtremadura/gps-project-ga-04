package es.unex.giiis.fitlife365.model

import java.io.Serializable

data class ExerciseModel (
    val name: String = "",
    val type: String = "",
    val muscle: String = "",
    val equipment: String = "",
    val difficulty: String = "",
    val instructions: String = "",
) : Serializable