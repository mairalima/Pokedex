package com.example.fintrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val imageUrl: String,
    val weight: Double,
    val height: Double,
    val baseExperience: Int,
    val types: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int
)

fun PokemonEntity.toPokemon(): Pokemon {

    val typesList = this.types.split(", ")

    return Pokemon(
        id = this.id, // Keep the id from the entity
        name = this.name,
        imageUrl = this.imageUrl,
        weight = this.weight,
        height = this.height,
        baseExperience = this.baseExperience,
        types = typesList,
        hp = this.hp,
        attack = this.attack,
        defense = this.defense,
        speed = this.speed
    )
}