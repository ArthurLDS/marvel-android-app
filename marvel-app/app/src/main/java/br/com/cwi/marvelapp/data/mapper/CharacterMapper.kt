package br.com.cwi.marvelapp.data.mapper

import br.com.cwi.marvelapp.data.model.remote.CharacterDataResponse
import br.com.cwi.marvelapp.domain.model.CharacterData

class CharacterMapper : DomainMapper<CharacterDataResponse, CharacterData> {

    override fun toDomain(from: CharacterDataResponse) = CharacterData(
        results = CharacterItemMapper().toDomain(from.results),
        total = from.total
    )

    override fun toDomain(from: List<CharacterDataResponse>) = from.map { toDomain(it) }
}

