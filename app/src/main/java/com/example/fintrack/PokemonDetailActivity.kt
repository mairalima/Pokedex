package com.example.fintrack

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.fintrack.database.PokemonDao
import com.example.fintrack.repository.PokemonRepository

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        val pokemonDao = PokemonDatabase.getDatabase(applicationContext).pokemonDAO()
        val pokemonRepository = PokemonRepository(pokemonDao)

        // Obtendo o ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(pokemonRepository)
        )[PokemonViewModel::class.java]

        // Obtendo dados passados via BD
        val pokemonId = intent.getIntExtra("pokemon_id", -1)
        Log.d("PokemonDetailActivity", "Received Pokemon ID: $pokemonId") // Log para depuração
        if (pokemonId != -1) {
            // Aqui você observa o LiveData
            viewModel.getPokemonById(pokemonId).observe(this, Observer { pokemon ->
                if (pokemon != null) {
                    // Atualizar a UI com os dados do Pokémon
                    updateUI(pokemon)
                } else {
                    // Exibir uma mensagem de erro se o Pokémon não for encontrado
                    showError("Pokemon not found")
                }
            })
        } else {
            showError("Invalid ID")
        }
    }

    private fun updateUI(pokemon: PokemonEntity) {
        Log.d("PokemonDetailActivity", "Updating UI with Pokémon: ${pokemon.name}")
        findViewById<TextView>(R.id.name_pokemon).text = pokemon.name
        findViewById<TextView>(R.id.textViewWeight).text = "Peso: ${pokemon.weight} kg"
        findViewById<TextView>(R.id.textViewHeight).text = "Altura: ${pokemon.height} m"
        findViewById<TextView>(R.id.textViewHP).text = "HP: ${pokemon.hp}"
        findViewById<TextView>(R.id.textViewATK).text = "Ataque: ${pokemon.attack}"
        findViewById<TextView>(R.id.textViewDEF).text = "Defesa: ${pokemon.defense}"
        findViewById<TextView>(R.id.textViewSPD).text = "Speed: ${pokemon.speed}"
        findViewById<TextView>(R.id.textViewEXP).text = "Experiencia: ${pokemon.baseExperience}"

        val typesList = pokemon.types.split(", ").map { it.trim() }

        // Handle type1 and type2 TextViews
        val type1 = findViewById<TextView>(R.id.type1)
        val type2 = findViewById<TextView>(R.id.type2)

        // Display the types, if available
        type1.text = typesList.getOrNull(0) ?: "N/A"  // First type (or "N/A" if not available)
        type2.text = typesList.getOrNull(1) ?: ""


        Glide.with(this)
            .load(pokemon.imageUrl)
            .override(500, 500)
            .into(findViewById(R.id.imageViewPokemon))
    }

    // Função para mostrar erro
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
