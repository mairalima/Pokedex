package com.example.fintrack

data class PokemonResponse(

    val results: List<PokemonDTO>

)

//data class para pegar os detalhes do pokemon
data class PokemonDetails(
    val sprites: Sprites
)