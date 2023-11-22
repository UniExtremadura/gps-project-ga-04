package es.unex.giiis.fitlife365.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Routine::class,
            parentColumns = ["routineId"],
            childColumns = ["routineId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseModel (
    @PrimaryKey(autoGenerate = true) var exerciseId: Long?,
    val name: String = "",
    val type: String = "",
    val muscle: String = "",
    val equipment: String = "",
    val difficulty: String = "",
    val instructions: String = "",
    var isSelected: Boolean = false,
    val routineId: Long? = null
) : Serializable