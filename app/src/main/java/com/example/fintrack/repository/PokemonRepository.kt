package com.example.fintrack.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.fintrack.api.PokeApi
import com.example.fintrack.data.PokemonDTO
import com.example.fintrack.data.PokemonEntity
import com.example.fintrack.response.PokemonResponse
import com.example.fintrack.api.RetrofitClient
import com.example.fintrack.database.PokemonDao
import com.example.fintrack.database.toPokemonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository(private val pokemonDao: PokemonDao) {

    private val pokeApi = RetrofitClient.retrofitInstance.create(PokeApi::class.java)


    fun getPokemonList(onResult: (List<PokemonEntity>) -> Unit) {
        pokeApi.getPokemon().enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                if (response.isSuccessful) {
                    val pokemonResults = response.body()?.results ?: emptyList()
                    Log.d("PokeApi", "Pokémons encontrados: ${pokemonResults.size}")
                    fetchDetailsForAllPokemon(pokemonResults, onResult)
                } else {
                    Log.e("PokeApi", "Request Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                Log.e("PokeApi", "Network Error: ${t.message}")
            }
        })
    }


    private fun fetchDetailsForAllPokemon(pokemonResults: List<PokemonDTO>, onResult: (List<PokemonEntity>) -> Unit) {
        val pokemonList = mutableListOf<PokemonEntity>()
        var requestsCompleted = 0

        for (pokemon in pokemonResults) {
            pokeApi.getPokemonDetails(pokemon.url).enqueue(object : Callback<PokemonDTO> {
                override fun onResponse(call: Call<PokemonDTO>, response: Response<PokemonDTO>) {
                    if (response.isSuccessful) {
                        response.body()?.let { pokemonDetails ->
                            val pokemonEntity = pokemonDetails.toPokemonEntity()
                            savePokemonToDatabase(pokemonEntity)

                            pokemonList.add(pokemonEntity)

                            // Logging Pokemon details for debugging
                            Log.d("PokeDetails", "Nome: ${pokemonDetails.name}")
                            pokemonDetails.stats.forEach { stat ->
                                Log.d("PokeStats", "${stat.stat.name}: ${stat.baseStat}")
                            }
                        }
                    } else {
                        Log.e("PokeDetails", "Erro ao buscar detalhes do Pokémon: ${response.errorBody()}")
                    }

                    requestsCompleted++
                    if (requestsCompleted == pokemonResults.size) {
                        onResult(pokemonList)
                    }
                }

                override fun onFailure(call: Call<PokemonDTO>, t: Throwable) {
                    Log.e("PokeDetails", "Network Error: ${t.message}")
                    requestsCompleted++
                    if (requestsCompleted == pokemonResults.size) {
                        onResult(pokemonList)
                    }
                }
            })
        }
    }


    private fun savePokemonToDatabase(pokemon: PokemonEntity) {

        kotlinx.coroutines.GlobalScope.launch {
            pokemonDao.insertPokemon(pokemon)
        }
    }


    suspend fun getLocalPokemon(): List<PokemonEntity> {
        return withContext(Dispatchers.IO) {
            pokemonDao.getAllPokemons()
        }

    }

    fun getPokemonById(id: Int): LiveData<PokemonEntity> {
        return pokemonDao.getPokemonById(id)
    }

    }

