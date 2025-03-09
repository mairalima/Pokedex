package com.example.fintrack

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.fintrack.database.PokemonDao

class PokemonAdapter(private var pokemonList: List<Pokemon>) : Adapter<PokemonAdapter.PokemonViewHolder>() {
    //view que segura os dados
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPokemon: ImageView = itemView.findViewById(R.id.imageViewPokemon)
        val namePokemon: TextView = itemView.findViewById(R.id.tv_rv_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_lista, parent, false)
        return PokemonViewHolder(view)
    }

    // FINALIZADO A IMPLEMENTAÇÃO DO onBindViewHolder
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        // ✅ Verifica se o nome não está nulo
        Log.d("Adapter", "Exibindo Pokémon: ${pokemon.name}")
        holder.namePokemon.text = pokemon.name
       // holder.typeTextView.text = "Tipo: ${pokemon.types}"
        //holder.statsTextView.text = "HP: ${pokemon.hp} | ATK: ${pokemon.attack} | DEF: ${pokemon.defense} | SPD: ${pokemon.speed}"
        //holder.weightHeightTextView.text = "Peso: ${pokemon.weight}kg | Altura: ${pokemon.height}m"


        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl)
            .into(holder.imgPokemon)
    }

    override fun getItemCount(): Int = pokemonList.size

    fun updateList(newList: List<Pokemon>) {
        pokemonList = newList
        notifyDataSetChanged()
    }
}
