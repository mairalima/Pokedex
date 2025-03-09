package com.example.fintrack

data class Pokemon(
    val name: String,
    val imageUrl: String,
    val weight: Double,
    val height: Double,
    val baseExperience: Int,
    val types: List<String>,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int,
    val id: Int
)
