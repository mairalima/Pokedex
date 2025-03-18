package com.example.fintrack.ui

import android.os.Bundle
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.fintrack.database.PokemonDatabase
import com.example.fintrack.data.PokemonEntity
import com.example.fintrack.viewmodel.PokemonViewModel
import com.example.fintrack.R
import com.example.fintrack.viewmodel.ViewModelFactory
import com.example.fintrack.repository.PokemonRepository
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils

@Suppress("DEPRECATION")
class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonViewModel

    private val typeColors = mapOf(
        "normal" to R.color.type_normal,
        "fire" to R.color.type_fire,
        "water" to R.color.type_water,
        "electric" to R.color.type_electric,
        "grass" to R.color.type_grass,
        "ice" to R.color.type_ice,
        "fighting" to R.color.type_fighting,
        "poison" to R.color.type_poison,
        "ground" to R.color.type_ground,
        "flying" to R.color.type_flying,
        "psychic" to R.color.type_psychic,
        "bug" to R.color.type_bug,
        "rock" to R.color.type_rock,
        "ghost" to R.color.type_ghost,
        "dragon" to R.color.type_dragon,
        "dark" to R.color.type_dark,
        "steel" to R.color.type_steel,
        "fairy" to R.color.type_fairy
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        val pokemonDao = PokemonDatabase.getDatabase(applicationContext).pokemonDAO()
        val pokemonRepository = PokemonRepository(pokemonDao)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(pokemonRepository)
        )[PokemonViewModel::class.java]

        val pokemonId = intent.getIntExtra("pokemon_id", -1)
        Log.d("PokemonDetailActivity", "Received Pokemon ID: $pokemonId")

        if (pokemonId != -1) {
            viewModel.getPokemonById(pokemonId).observe(this, Observer { pokemon ->
                if (pokemon != null) {
                    updateUI(pokemon)
                } else {
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
        findViewById<TextView>(R.id.textViewPokemonNumber).text = "#${pokemon.id}"

        val hpBar = findViewById<ProgressBar>(R.id.progressBarHP)
        val atkBar = findViewById<ProgressBar>(R.id.progressBarATK)
        val defBar = findViewById<ProgressBar>(R.id.progressBarDEF)
        val spdBar = findViewById<ProgressBar>(R.id.progressBarSPD)
        val expBar = findViewById<ProgressBar>(R.id.progressBarEXP)

// Aplicar cores diferentes para cada ProgressBar
        hpBar.progressDrawable.setColorFilter(
            Color.parseColor("#FFD700"),
            PorterDuff.Mode.SRC_IN
        )
        atkBar.progressDrawable.setColorFilter(
            Color.parseColor("#8B0000"),
            PorterDuff.Mode.SRC_IN
        )
        defBar.progressDrawable.setColorFilter(
            Color.parseColor("#00008B"),
            PorterDuff.Mode.SRC_IN
        )
        spdBar.progressDrawable.setColorFilter(
            Color.parseColor("#40E0D0"),
            PorterDuff.Mode.SRC_IN
        )
        expBar.progressDrawable.setColorFilter(
            Color.parseColor("#008000"),
            PorterDuff.Mode.SRC_IN
        )

// Definir os valores nos ProgressBars
        hpBar.progress = pokemon.hp
        atkBar.progress = pokemon.attack
        defBar.progress = pokemon.defense
        spdBar.progress = pokemon.speed
        expBar.progress = pokemon.baseExperience

        val typesList = pokemon.types.split(", ").map { it.trim() }
        val type1TextView = findViewById<TextView>(R.id.type1)
        val type2TextView = findViewById<TextView>(R.id.type2)

        type1TextView.text = typesList.getOrNull(0) ?: ""
        type2TextView.text = typesList.getOrNull(1) ?: ""



        if (typesList.size == 1) {
            type2TextView.visibility = View.GONE
        } else {
            type2TextView.visibility = View.VISIBLE
        }

        // Obtém a cor do tipo ou usa uma cor padrão
        val type1Color =
            typeColors[typesList.getOrNull(0)]?.let { ContextCompat.getColor(this, it) }
        val type2Color =
            typeColors[typesList.getOrNull(1)]?.let { ContextCompat.getColor(this, it) }

// Aplica a cor no texto se o tipo existir
        type1Color?.let { type1TextView.setTextColor(it) }
        type2Color?.let { type2TextView.setTextColor(it) }

        Glide.with(this)
            .load(pokemon.imageUrl)
            .override(500, 500)
            .into(findViewById(R.id.imageViewPokemon))

        val typeColorsList = typesList.mapNotNull { typeColors[it] }
            .map { ContextCompat.getColor(this, it) }

        val baseColor = if (typeColorsList.size > 1) {
            ColorUtils.blendARGB(typeColorsList[0], typeColorsList[1], 0.5f)
        } else {
            typeColorsList.firstOrNull() ?: ContextCompat.getColor(this, R.color.type_normal)
        }

        val softColor = ColorUtils.blendARGB(baseColor, Color.WHITE, 0.1f)
        val variedColor = ColorUtils.blendARGB(softColor, Color.WHITE, (pokemon.id % 4) * 0.1f)

        setRoundedBackgroundColor(findViewById(R.id.type1), variedColor)
        setRoundedBackgroundColor(findViewById(R.id.type2), variedColor)
        setRoundedBackgroundColor(findViewById(R.id.RelativeLayoutImage), variedColor)
        type1TextView.setTextColor(Color.WHITE)
        type2TextView.setTextColor(Color.WHITE)
    }

    private fun setRoundedBackgroundColor(view: View, color: Int) {
        val background = view.background as? GradientDrawable
        background?.setColor(color)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
