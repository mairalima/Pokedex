package com.example.fintrack

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.fintrack.database.PokemonDao
import com.bumptech.glide.request.target.Target
import jp.wasabeef.glide.transformations.CropSquareTransformation
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
        Color.rgb(255, 105, 180), // Rosa vibrante
        Color.rgb(255, 165, 0),   // Laranja vibrante
        Color.rgb(0, 191, 255),   // Azul claro vibrante
        Color.rgb(173, 255, 47),  // Verde claro vibrante
        Color.rgb(255, 215, 0),   // Dourado vibrante
        Color.rgb(238, 130, 238), // Violeta vibrante
        Color.rgb(220, 20, 60),    // Vermelho vibrante
        Color.rgb(64, 224, 208)   // Turquesa vibrante
    )

    // MÉTODO PARA GERAR CORES PERSONALIZADAS DE ACORDO COM O POKEMON
    fun generatePokemonColor(baseColor: Int, pokemonId: Int): Int {
        val uniqueColor = colorPalette[pokemonId % colorPalette.size]
        return ColorUtils.blendARGB(uniqueColor, Color.WHITE, 0.3f) // Mistura com branco (30%)
    }

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

        // Verifica se o nome não está nulo
        Log.d("Adapter", "Exibindo Pokémon: ${pokemon.name}")
        holder.namePokemon.text = pokemon.name
        //holder.weightPokemon.text = "Peso: ${pokemon.weight}kg "
        //holder.heightPokemon.text = "Altura: ${pokemon.height}m"
        //holder.typeTextView.text = "Tipo: ${pokemon.types}"
        //holder.statsTextView.text = "HP: ${pokemon.hp} | ATK: ${pokemon.attack} | DEF: ${pokemon.defense} | SPD: ${pokemon.speed}"


        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl)
            .override(250, 250) // Força um tamanho uniforme
            .apply(RequestOptions.bitmapTransform(CropTransformation(250, 250))) // Recorta fundo extra
            .centerInside() // Mantém a proporção sem cortar partes importantes
            .into(holder.imgPokemon)

        // Obter o tipo do Pokémon
        val pokemonType = pokemon.types.firstOrNull() ?: "normal"
        val baseColorResId = typeColors[pokemonType] ?: R.color.type_normal
        val baseColor = ContextCompat.getColor(holder.itemView.context, baseColorResId)

        // Suavizar a cor base misturando com branco (10% mais claro)
        val softColor = ColorUtils.blendARGB(baseColor, Color.WHITE, 0.1f)

        // Variar o tom para diferenciar Pokémons do mesmo tipo (baseado no ID)
        val variedColor = ColorUtils.blendARGB(softColor, Color.WHITE, (pokemon.id % 4) * 0.1f)

        // Aplicar a cor final ao fundo do item
        setRoundedBackgroundColor(holder.itemView, variedColor)

        holder.itemView.setOnClickListener{
            pokClickListener?.invoke(pokemon)
        }
    }

    // NOVO MÉTODO CRIADO PARA CONFIGURAR O BACKGROUND
    private fun setRoundedBackgroundColor(view: View, color: Int) {
        val background = view.background as? GradientDrawable
        if (background != null) {
            background.setColor(color)
        }
    }

    override fun getItemCount(): Int = pokemonList.size

    // HOUVE ALTERAÇÃO NESSA LINHA
    fun updateList(newList: List<Pokemon>) {
        pokemonList = newList.shuffled() // Adicionei o "shuffled" para não ser exibido pokemons do mesmo tipo
        notifyDataSetChanged()
    }


}