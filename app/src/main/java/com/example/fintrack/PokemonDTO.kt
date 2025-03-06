package com.example.fintrack

import com.google.gson.annotations.SerializedName

data class PokemonDTO(
    val name: String,
    val url: String,

)
//criei assim só para lembrar que esta buscando a imagem, dentro da url a proprieda sprites contem o front_default com a imagem
data class Sprites (
    @SerializedName("front_default")
    val frontDefault: String?
){
       val image: String?
        get() = frontDefault
}