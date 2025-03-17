package com.example.fintrack.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintrack.data.Pokemon
import com.example.fintrack.data.PokemonEntity
import com.example.fintrack.data.toPokemon
import com.example.fintrack.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList


    //
    fun fetchPokemonData() {

        viewModelScope.launch(Dispatchers.IO) {
            val localPokemons = repository.getLocalPokemon()
            if (localPokemons.isNotEmpty()) {

                val localMappedList = localPokemons.map { it.toPokemon() }
                _pokemonList.postValue(localMappedList)
            } else {

                repository.getPokemonList { pokemonEntityList ->

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



