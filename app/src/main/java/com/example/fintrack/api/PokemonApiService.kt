package com.example.fintrack.api

//import android.telecom.Call
import com.example.fintrack.response.PokemonResponse
import com.example.fintrack.data.PokemonDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface PokeApi {

    @GET("pokemon?limit=10000")
    fun getPokemon() : Call<PokemonResponse>

    //requisicao para pegar a imagem e outros detalhes
    @GET
    fun getPokemonDetails(@Url url: String): Call<PokemonDTO>
}


