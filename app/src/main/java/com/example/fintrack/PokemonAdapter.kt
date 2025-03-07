package com.example.fintrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class PokemonAdapter(private val pokemonList: List<Pokemon>

// ARGUMENTO PARA O ONITENCLICK LISTENER
/*, private val onItemClick: (Int) -> Unit*/

) : Adapter<PokemonAdapter.PokemonViewHolder>() {
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
        holder.namePokemon.text = pokemon.name

        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl)
            .into(holder.imgPokemon)

        // ADICIONANDO O ONCLICKLISTENER
        /*holder.itemView.setOnClickListener {
            onItemClick(position)
        }*/
    }

    override fun getItemCount(): Int {
        return pokemonList.size

    }

}

