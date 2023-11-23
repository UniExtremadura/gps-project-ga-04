package es.unex.giiis.fitlife365.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Routine (
    @PrimaryKey(autoGenerate = true) var routineId: Long?,
    var userId: Long?,
    val name: String = "",
    val pesoObjetivo: Int = 0,
    val diasEntrenamiento: String = "",
    var ejercicios: String = ""
) : Serializable