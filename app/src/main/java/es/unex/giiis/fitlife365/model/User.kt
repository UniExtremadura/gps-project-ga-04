package es.unex.giiis.fitlife365.model

import java.io.Serializable

data class User(
    var name: String = "",
    var password: String = ""
) : Serializable
