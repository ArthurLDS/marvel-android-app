package br.com.cwi.marvelapp.domain.repository

import br.com.cwi.marvelapp.domain.model.CharacterData
import br.com.cwi.marvelapp.domain.model.CharacterItem

interface CharacterRepository {

    //Remote
    suspend fun getCharacters(limit: Int, offset: Int, term: String? = null): CharacterData
    suspend fun getCharacterDetail(id: Long): CharacterItem

}