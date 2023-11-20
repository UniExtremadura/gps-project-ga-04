package es.unex.giiis.fitlife365.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ExerciseModel (
    @PrimaryKey(autoGenerate = true) var exerciseId: Long?,
    val name: String = "",
    val type: String = "",
    val muscle: String = "",
    val equipment: String = "",
    val difficulty: String = "",
    val instructions: String = ""
) : Serializable