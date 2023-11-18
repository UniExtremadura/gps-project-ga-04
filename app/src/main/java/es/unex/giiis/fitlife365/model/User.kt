package es.unex.giiis.fitlife365.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var userId: Long?,
    val name: String = "",
    val password: String = "",
    val email: String = ""
) : Serializable

