package com.example.fintrack.database

import com.example.fintrack.data.PokemonDTO
import com.example.fintrack.data.PokemonEntity
import com.example.fintrack.data.Sprites
import com.example.fintrack.data.Stat
import com.example.fintrack.data.StatInfo
import com.example.fintrack.data.TypeInfo
import com.example.fintrack.data.TypeSlot

fun PokemonDTO.toPokemonEntity(): PokemonEntity {

    val typesString = this.types.joinToString(", ") { it.type.name }

    return PokemonEntity(
        id = this.id,
        name = this.name,
        imageUrl = this.sprites.frontDefault ?: "",
        weight = this.weight / 10.0,
        height = this.height / 10.0,
        baseExperience = this.baseExperience,
        types = typesString,
        hp = this.stats.find { it.stat.name == "hp" }?.baseStat ?: 0,
        attack = this.stats.find { it.stat.name == "attack" }?.baseStat ?: 0,
        defense = this.stats.find { it.stat.name == "defense" }?.baseStat ?: 0,
        speed = this.stats.find { it.stat.name == "speed" }?.baseStat ?: 0
    )
}


fun PokemonEntity.toPokemonDTO(): PokemonDTO {

    val typesList = this.types.split(", ").map { TypeSlot(0, TypeInfo(it)) }

    return PokemonDTO(
        id = this.id,
        name = this.name,
        url = "",
        sprites = Sprites(frontDefault = this.imageUrl),
        weight = (this.weight * 10).toInt(),
        height = (this.height * 10).toInt(),
        baseExperience = this.baseExperience,
        types = typesList,
        stats = listOf(
            Stat(baseStat = this.hp, stat = StatInfo("hp")),
            Stat(baseStat = this.attack, stat = StatInfo("attack")),
            Stat(baseStat = this.defense, stat = StatInfo("defense")),
            Stat(baseStat = this.speed, stat = StatInfo("speed"))
        )
    )
}
