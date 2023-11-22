package es.unex.giiis.fitlife365.data

import com.google.gson.annotations.SerializedName

class Exercise (
    @SerializedName("name"         ) var name         : String? = null,
    @SerializedName("type"         ) var type         : String? = null,
    @SerializedName("muscle"       ) var muscle       : String? = null,
    @SerializedName("equipment"    ) var equipment    : String? = null,
    @SerializedName("difficulty"   ) var difficulty   : String? = null,
    @SerializedName("instructions" ) var instructions : String? = null
)