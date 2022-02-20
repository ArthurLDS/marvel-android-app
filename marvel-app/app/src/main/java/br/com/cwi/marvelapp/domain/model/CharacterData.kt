package br.com.cwi.marvelapp.domain.model

data class CharacterData(
    val results: List<CharacterItem>,
    val total: Int
)

