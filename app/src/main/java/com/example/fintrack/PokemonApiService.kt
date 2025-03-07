package com.example.fintrack

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface PokeApi {

    @GET("pokemon?limit=20")
    fun getPokemon() : Call<PokemonResponse>

    //requisicao para pegar a imagem e outros detalhes
    @GET
    fun getPokemonDetails(@Url url: String): Call<PokemonDTO>
}


