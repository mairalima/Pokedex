package com.example.fintrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerviewActivity: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pokemonList = listOf(
            Pokemon(
                "Pokemon"
            )
        )

        recyclerviewActivity = findViewById(R.id.activity_recyclerview)
        recyclerviewActivity.adapter = PokemonAdapter(pokemonList)

        // recyclerviewActivity.layoutManager
    }
}