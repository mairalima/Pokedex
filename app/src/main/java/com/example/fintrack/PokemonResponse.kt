package com.example.fintrack

import com.google.gson.annotations.SerializedName

data class PokemonResponse(

    val results: List<PokemonDTO>

)

//data class para pegar os detalhes do pokemon
data class PokemonDetails(
    val sprites: Sprites,
    val weight: Int,
    val height: Int,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val types: List<TypeSlot>,
    val stats: List<Stat>
)

