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
    var sexo: String = "",
    var edad: Int = 0,
    var altura: Int = 0,
    var peso: Int = 0
) : Serializable