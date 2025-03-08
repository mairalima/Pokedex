package com.example.fintrack

// Função para converter de PokemonDTO para PokemonEntity
fun PokemonDTO.toPokemonEntity(): PokemonEntity {
    // Concatenar os tipos em uma única string separada por vírgula
    val typesString = this.types.joinToString(", ") { it.type.name }

    return PokemonEntity(
        id = this.id,
        name = this.name,
        imageUrl = this.sprites.frontDefault ?: "", // Se a imagem for nula, retorna uma string vazia
        weight = this.weight / 10.0, // Convertendo de gramas para kg
        height = this.height / 10.0, // Convertendo de centímetros para metros
        baseExperience = this.baseExperience,
        types = typesString, // A lista de tipos é convertida para uma string
        hp = this.stats.find { it.stat.name == "hp" }?.baseStat ?: 0,
        attack = this.stats.find { it.stat.name == "attack" }?.baseStat ?: 0,
        defense = this.stats.find { it.stat.name == "defense" }?.baseStat ?: 0,
        speed = this.stats.find { it.stat.name == "speed" }?.baseStat ?: 0
    )
}

// Função para converter de PokemonEntity para PokemonDTO
fun PokemonEntity.toPokemonDTO(): PokemonDTO {
    // Dividir os tipos armazenados como uma string em uma lista novamente
    val typesList = this.types.split(", ").map { TypeSlot(0, TypeInfo(it)) }

    return PokemonDTO(
        id = this.id,
        name = this.name,
        url = "", // A URL pode ser preenchida se necessário
        sprites = Sprites(frontDefault = this.imageUrl),
        weight = (this.weight * 10).toInt(), // Convertendo de kg para gramas
        height = (this.height * 10).toInt(), // Convertendo de metros para centímetros
        baseExperience = this.baseExperience,
        types = typesList, // Convertemos a string de volta para uma lista de objetos
        stats = listOf(
            Stat(baseStat = this.hp, stat = StatInfo("hp")),
            Stat(baseStat = this.attack, stat = StatInfo("attack")),
            Stat(baseStat = this.defense, stat = StatInfo("defense")),
            Stat(baseStat = this.speed, stat = StatInfo("speed"))
        )
    )
}
