package com.example.fintrack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fetch list of Pokémon when the activity is created
        fetchPokemonData()
    }


    private fun fetchPokemonData() {
        val pokeApi = RetrofitClient.retrofitInstance.create(PokeApi::class.java)
        val callPokemon = pokeApi.getPokemon()

        callPokemon.enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(
                call: Call<PokemonResponse>, response: Response<PokemonResponse>
            ) {
                if (response.isSuccessful) {
                    val pokemonList = response.body()?.results
                    if (pokemonList != null) {
                        for (poke in pokemonList) {
                            Log.d("PokeApi", "Pokemon: ${poke.name}")

                            //chamando funcao para buscar os detalhes
                            fetchPokemonDetails(poke.url)
                        }
                    }
                } else {
                    Log.e("PokeApi", "Request Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                Log.e("PokeApi", "Network Error: ${t.message}")
            }
        })
    }


    private fun fetchPokemonDetails(url: String) {
        val pokeApi = RetrofitClient.retrofitInstance.create(PokeApi::class.java)
        val callDetails = pokeApi.getPokemonDetails(url)

        callDetails.enqueue(object : Callback<PokemonDetails> {
            override fun onResponse(
                call: Call<PokemonDetails>, response: Response<PokemonDetails>
            ) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    if (pokemon != null) {
                        val types = pokemon.types.joinToString { it.type.name }
                        val weightKg = pokemon.weight / 10.0
                        val heightM = pokemon.height / 10.0
                        val hp = pokemon.stats.find { it.stat.name == "hp" }?.baseStat ?: 0
                        val atk = pokemon.stats.find { it.stat.name == "attack" }?.baseStat ?: 0
                        val def = pokemon.stats.find { it.stat.name == "defense" }?.baseStat ?: 0
                        val spd = pokemon.stats.find { it.stat.name == "speed" }?.baseStat ?: 0
                        val exp = pokemon.baseExperience
                        Log.d("Image", "Imagem Pokemon: $pokemon.sprites.image")
                        Log.d("PokeDetails", "Types: $types")
                        Log.d("PokeDetails", "Weight: ${weightKg}kg")
                        Log.d("PokeDetails", "Height: ${heightM}m")
                        Log.d("PokeDetails", "HP: $hp")
                        Log.d("PokeDetails", "ATK: $atk")
                        Log.d("PokeDetails", "DEF: $def")
                        Log.d("PokeDetails", "SPD: $spd")
                        Log.d("PokeDetails", "EXP: $exp")

                    }
                } else {
                    Log.e("Image", "Request Error: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                Log.e("Image", "Network Error: ${t.message}")
            }
        })
    }
}