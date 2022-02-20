package br.com.cwi.marvelapp.data.repositoty

import br.com.cwi.marvelapp.data.mapper.CharacterItemMapper
import br.com.cwi.marvelapp.data.mapper.CharacterMapper
import br.com.cwi.marvelapp.data.source.remote.MarvelApi
import br.com.cwi.marvelapp.domain.model.CharacterData
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val api: MarvelApi,
    private val mapperCharacterData: CharacterMapper,
    private val mapperCharacterItem: CharacterItemMapper
) : CharacterRepository {

    override suspend fun getCharacters(limit: Int, offset: Int, term: String?): CharacterData {
        return mapperCharacterData.toDomain(api.getCharacters(limit, offset, term).data)
    }

    override suspend fun getCharacterDetail(id: Long): CharacterItem {
        return mapperCharacterItem.toDomain(api.getCharacterDetail(id).data.results.first())
    }

}