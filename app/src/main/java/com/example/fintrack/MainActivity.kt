package com.example.fintrack

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fintrack.repository.PokemonRepository

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter

    // Usando o ViewModelFactory para passar o PokemonRepository com o pokemonDao
    private val viewModel: PokemonViewModel by viewModels {
        val pokemonDao = PokemonDatabase.getDatabase(applicationContext).pokemonDAO() // Obtenha o pokemonDao
        val pokemonRepository = PokemonRepository(pokemonDao) // Crie o Repository com o pokemonDao
        ViewModelFactory(pokemonRepository) // Passe o Repository para o ViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuração do RecyclerView
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        pokemonAdapter = PokemonAdapter(emptyList()) // Inicializa com lista vazia
        recyclerView.adapter = pokemonAdapter

        // Observar mudanças na lista de Pokémon
        viewModel.pokemonList.observe(this, Observer { pokemonList ->
            pokemonAdapter.updateList(pokemonList)
        })

        // Chama a função para buscar dados
        viewModel.fetchPokemonData()
    }
}
