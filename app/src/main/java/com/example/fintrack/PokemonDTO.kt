package com.example.fintrack

import com.google.gson.annotations.SerializedName

data class PokemonDTO(
    val name: String,
    val url: String,
    val sprites: Sprites,
    val weight: Int,
    val height: Int,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val types: List<TypeSlot>,
    val stats: List<Stat>
)
data class TypeSlot(
    val slot: Int,
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: StatInfo
)

data class StatInfo (
    val name: String
)

//criei assim só para lembrar que esta buscando a imagem, dentro da url a proprieda sprites contem o front_default com a imagem
data class Sprites (
    @SerializedName("front_default")
    val frontDefault: String?
)


fun PokemonDTO.toPokemon(): Pokemon {
    return Pokemon(
        name = this.name,
        imageUrl = this.sprites.frontDefault ?: "" // Se a imagem for nula, retorna ""
    )
}
