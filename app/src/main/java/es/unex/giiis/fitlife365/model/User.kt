package es.unex.giiis.fitlife365.model

import java.io.Serializable

data class User(
    val name: String = "",
    val password: String = ""
) : Serializable
