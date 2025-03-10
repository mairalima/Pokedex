package com.example.fintrack

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintrack.Pokemon
import com.example.fintrack.PokemonDTO
import com.example.fintrack.repository.PokemonRepository
import com.example.fintrack.toPokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList


    //
    fun fetchPokemonData() {
        // checando o banco primeiramente
        viewModelScope.launch(Dispatchers.IO) {
            val localPokemons = repository.getLocalPokemon()
            if (localPokemons.isNotEmpty()) {
                // se tiver mostra na UI
                val localMappedList = localPokemons.map { it.toPokemon() }
                _pokemonList.postValue(localMappedList)
            } else {
                // se nao carregar do banco busca na Api
                repository.getPokemonList { pokemonEntityList ->
                    // lista da api p UI
                    val mappedList = pokemonEntityList.map { it.toPokemon() }
                    _pokemonList.postValue(mappedList)
                }
            }
        }
    }

    fun getPokemonById(id: Int): LiveData<PokemonEntity> {
        Log.d("PokemonViewModel", "Fetching Pokemon with ID: $id")
        return repository.getPokemonById(id)
    }
}



/*class PokemonViewModel : ViewModel() {

    private val repository = PokemonRepository()
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    fun fetchPokemonData() {
        repository.getPokemonList { pokemonDTOList ->
            val mappedList = pokemonDTOList.map { it.toPokemon() }
            _pokemonList.postValue(mappedList)
        }
    }
}*/
