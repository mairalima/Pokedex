package com.example.fintrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class PokemonAdapter(
    private val lista: List<Pokemon>
) : Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item_lista,
            parent,
            false
        )
        return PokemonViewHolder(view)
    }

    override fun getItemCount() = lista.size

    // FINALIZAR A IMPLEMENTAÇÃO DO onBindViewHolder
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = lista[position]
    }

    inner class PokemonViewHolder(
        val itemView: View
    ) : ViewHolder(itemView) {

    }

}