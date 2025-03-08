package com.example.fintrack

import com.google.gson.annotations.SerializedName

data class PokemonDTO(
    val id : Int,
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
    val typesList = types.map { it.type.name } // Create a List<String> from the types
    val hp = stats.find { it.stat.name == "hp" }?.baseStat ?: 0
    val atk = stats.find { it.stat.name == "attack" }?.baseStat ?: 0
    val def = stats.find { it.stat.name == "defense" }?.baseStat ?: 0
    val spd = stats.find { it.stat.name == "speed" }?.baseStat ?: 0

    return Pokemon(
        name = this.name,
        imageUrl = this.sprites.frontDefault ?: "", // imagem vazia, retorna lista vazia
        weight = this.weight / 10.0, //converter para Kg
        height = this.height / 10.0, //converter para m
        baseExperience = this.baseExperience,
        types = typesList, // retornando uma list<strings)
        hp = hp,
        attack = atk,
        defense = def,
        speed = spd,
        id = this.id
    )
}