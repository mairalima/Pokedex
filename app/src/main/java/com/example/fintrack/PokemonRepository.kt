package com.example.fintrack.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.fintrack.PokeApi
import com.example.fintrack.PokemonDTO
import com.example.fintrack.PokemonEntity
import com.example.fintrack.PokemonResponse
import com.example.fintrack.RetrofitClient
import com.example.fintrack.database.PokemonDao
import com.example.fintrack.toPokemonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository(private val pokemonDao: PokemonDao) {

    private val pokeApi = RetrofitClient.retrofitInstance.create(PokeApi::class.java)

    // funcao para carregar o pokemon em lista direto da api
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

    //funcao p trazer os detalhes
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

                    // Check if all requests have been completed
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

    // funçao p salvar em banco de dados
    private fun savePokemonToDatabase(pokemon: PokemonEntity) {
        // Using a proper scope for database operations
        kotlinx.coroutines.GlobalScope.launch {
            pokemonDao.insertPokemon(pokemon)
        }
    }

    // funcao p pegar o pokemon do banco
    suspend fun getLocalPokemon(): List<PokemonEntity> {
        return withContext(Dispatchers.IO) {
            pokemonDao.getAllPokemons()
        }

    }

    fun getPokemonById(id: Int): LiveData<PokemonEntity> {
        return pokemonDao.getPokemonById(id)
    }

    }

