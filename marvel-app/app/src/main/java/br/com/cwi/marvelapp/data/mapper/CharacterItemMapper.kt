package br.com.cwi.marvelapp.data.mapper

import br.com.cwi.marvelapp.data.model.CharacterItemResponse
import br.com.cwi.marvelapp.domain.model.CharacterItem

class CharacterItemMapper : DomainMapper<CharacterItemResponse, CharacterItem> {
    override fun toDomain(from: CharacterItemResponse) = CharacterItem(
        id = from.id,
        name = from.name,
        description = from.description,
        isFavorite = from.isFavorite,
        series = from.series?.let { SeriesMapper().toDomain(it) },
        thumbnail = from.thumbnail?.let { ThumbnailMapper().toDomain(it) },
        comics = from.comics?.let { ComicsMapper().toDomain(it) }
    )

    override fun toDomain(from: List<CharacterItemResponse>) = from.map { toDomain(it) }
}