package br.com.cwi.marvelapp.domain

import br.com.cwi.marvelapp.data.model.Character
import br.com.cwi.marvelapp.data.model.CharacterDataResponse
import br.com.cwi.marvelapp.data.model.CharactersResponse

interface CharacterRepository {

    //Remote
    suspend fun getCharacters(limit: Int, offset: Int, term: String? = null): CharacterDataResponse
    suspend fun getCharacterDetail(id: Long): Character

}