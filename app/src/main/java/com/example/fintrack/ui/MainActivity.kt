package com.example.fintrack.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fintrack.ui.adapter.PokemonAdapter
import com.example.fintrack.database.PokemonDatabase
import com.example.fintrack.viewmodel.PokemonViewModel
import com.example.fintrack.R
import com.example.fintrack.viewmodel.ViewModelFactory
import com.example.fintrack.repository.PokemonRepository

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter


    private val viewModel: PokemonViewModel by viewModels {
        val pokemonDao = PokemonDatabase.getDatabase(applicationContext).pokemonDAO()
        val pokemonRepository = PokemonRepository(pokemonDao)
        ViewModelFactory(pokemonRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        pokemonAdapter = PokemonAdapter(emptyList()) // Inicializa com lista vazia
        recyclerView.adapter = pokemonAdapter

        pokemonAdapter.setOnclickListener { pokemon ->
            val intent = Intent(this, PokemonDetailActivity::class.java)
            intent.putExtra("pokemon_id", pokemon.id)
            startActivity(intent)
            Log.d("Mostre Click", pokemon.toString())
        }

        viewModel.pokemonList.observe(this, Observer { pokemonList ->
            pokemonAdapter.updateList(pokemonList)
        })


        viewModel.fetchPokemonData()
    }


}
