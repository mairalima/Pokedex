package com.example.fintrack.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fintrack.data.Pokemon
import com.example.fintrack.R
import jp.wasabeef.glide.transformations.CropTransformation

class PokemonAdapter(private var pokemonList: List<Pokemon>) : Adapter<PokemonAdapter.PokemonViewHolder>() {

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

    val colorPalette = arrayOf(
        Color.rgb(255, 105, 180),
        Color.rgb(255, 165, 0),
        Color.rgb(0, 191, 255),
        Color.rgb(173, 255, 47),
        Color.rgb(255, 215, 0),
        Color.rgb(238, 130, 238),
        Color.rgb(220, 20, 60),
        Color.rgb(64, 224, 208)
    )


    fun generatePokemonColor(baseColor: Int, pokemonId: Int): Int {
        val uniqueColor = colorPalette[pokemonId % colorPalette.size]
        return ColorUtils.blendARGB(uniqueColor, Color.WHITE, 0.3f)
    }

    private var pokClickListener: ((Pokemon) -> Unit)? = null

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



    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]


        Log.d("Adapter", "Exibindo Pokémon: ${pokemon.name}")
        holder.namePokemon.text = pokemon.name


        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl)
            .override(250, 250)
            .apply(RequestOptions.bitmapTransform(CropTransformation(250, 250)))
            .centerInside()
            .into(holder.imgPokemon)


        val typeColorsList = pokemon.types.mapNotNull { typeColors[it]?.let { resId ->
            ContextCompat.getColor(holder.itemView.context, resId) }
        }

        val mixedColor = when (typeColorsList.size) {
            2 -> ColorUtils.blendARGB(typeColorsList[0], typeColorsList[1], 0.5f)
            1 -> typeColorsList[0]
            else -> ContextCompat.getColor(holder.itemView.context, R.color.type_normal)
        }

        val softColor = ColorUtils.blendARGB(mixedColor, Color.WHITE, 0.1f)


        val variedColor = ColorUtils.blendARGB(softColor, Color.WHITE, (pokemon.id % 4) * 0.1f)


        setRoundedBackgroundColor(holder.itemView, variedColor)


        holder.itemView.setOnClickListener{
            pokClickListener?.invoke(pokemon)
        }
    }


    private fun setRoundedBackgroundColor(view: View, color: Int) {
        val background = view.background as? GradientDrawable
        if (background != null) {
            background.setColor(color)
        }
    }

    override fun getItemCount(): Int = pokemonList.size


    fun updateList(newList: List<Pokemon>) {
        pokemonList = newList.shuffled()
        notifyDataSetChanged()
    }


}