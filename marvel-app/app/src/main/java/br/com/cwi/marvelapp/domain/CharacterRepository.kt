package br.com.cwi.marvelapp.domain

import br.com.cwi.marvelapp.data.model.Character
import br.com.cwi.marvelapp.data.model.CharactersResponse

interface CharacterRepository {

    //Remote
    suspend fun getCharacters(limit: Int, offset: Int): CharactersResponse
    suspend fun getCharacterDetail(id: Long): Character

}