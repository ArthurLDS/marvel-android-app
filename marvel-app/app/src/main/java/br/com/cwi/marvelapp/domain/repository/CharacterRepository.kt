package br.com.cwi.marvelapp.domain.repository

import br.com.cwi.marvelapp.domain.model.CharacterData
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.model.Item

interface CharacterRepository {

    //Remote
    suspend fun getCharacters(limit: Int, offset: Int, term: String? = null): CharacterData
    suspend fun getCharacterDetail(id: Long): CharacterItem
    suspend fun getSeries(id: Long): List<Item>
    suspend fun getComics(id: Long): List<Item>

    //Local
    fun add(character: CharacterItem)
    fun delete(character: CharacterItem)
    fun getAll(): List<CharacterItem>
    fun getCharacterById(id: Long): CharacterItem?

}