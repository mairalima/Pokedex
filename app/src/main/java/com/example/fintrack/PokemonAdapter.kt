package com.example.fintrack

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.fintrack.database.PokemonDao

class PokemonAdapter(private var pokemonList: List<Pokemon>) : Adapter<PokemonAdapter.PokemonViewHolder>() {

    private var pokClickListener: ((Pokemon) -> Unit)? = null
    //view que segura os dados
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPokemon: ImageView = itemView.findViewById(R.id.imageViewPokemon)
        val namePokemon: TextView = itemView.findViewById(R.id.name_pokemon)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_lista, parent, false)
        return PokemonViewHolder(view)
    }

    fun setOnclickListener(onClick: (Pokemon) -> Unit) {
        pokClickListener = onClick
    }


    // FINALIZADO A IMPLEMENTAÇÃO DO onBindViewHolder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        // ✅ Verifica se o nome não está nulo
        Log.d("Adapter", "Exibindo Pokémon: ${pokemon.name}")
        holder.namePokemon.text = pokemon.name
        //holder.weightPokemon.text = "Peso: ${pokemon.weight}kg "
        //holder.heightPokemon.text = "Altura: ${pokemon.height}m"
        //holder.typeTextView.text = "Tipo: ${pokemon.types}"
        //holder.statsTextView.text = "HP: ${pokemon.hp} | ATK: ${pokemon.attack} | DEF: ${pokemon.defense} | SPD: ${pokemon.speed}"



        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl)
            .into(holder.imgPokemon)

        holder.itemView.setOnClickListener{
          pokClickListener?.invoke(pokemon)
        }
    }

   override fun getItemCount(): Int = pokemonList.size

    fun updateList(newList: List<Pokemon>) {
        pokemonList = newList
        notifyDataSetChanged()
    }


}
