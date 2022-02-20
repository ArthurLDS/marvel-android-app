package br.com.cwi.marvelapp.data.repositoty

import br.com.cwi.marvelapp.data.model.Character
import br.com.cwi.marvelapp.data.model.CharacterDataResponse
import br.com.cwi.marvelapp.data.source.remote.MarvelApi
import br.com.cwi.marvelapp.domain.CharacterRepository

class CharacterRepositoryImpl(private val api: MarvelApi): CharacterRepository {

    override suspend fun getCharacters(limit: Int, offset: Int, term: String?): CharacterDataResponse {
        return api.getCharacters(limit, offset, term).data
    }

    override suspend fun getCharacterDetail(id: Long): Character {
        return api.getCharacterDetail(id).data.results.first()
    }

}