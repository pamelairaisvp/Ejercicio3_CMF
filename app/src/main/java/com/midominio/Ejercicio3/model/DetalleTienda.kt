package com.midominio.Ejercicio3.model

import com.google.gson.annotations.SerializedName

class DetalleTienda {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("imag_url")
    var imag_url: String? = null

    @SerializedName("desc")
    var desc: String? = null

}