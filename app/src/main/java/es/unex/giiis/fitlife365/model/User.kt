package es.unex.giiis.fitlife365.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var userId: Long?,
    val name: String = "",
    val password: String = "",
    val email: String = "",
    val sexo: String = "",
    val edad: Int = 0,
    val altura: Int = 0,
    val peso: Int = 0
) : Serializable