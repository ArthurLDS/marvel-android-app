package br.com.cwi.marvelapp.data.mapper

import br.com.cwi.marvelapp.data.model.local.CharacterItemLocal
import br.com.cwi.marvelapp.data.model.remote.CharacterItemResponse
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

    fun fromDomain(from: CharacterItem) = CharacterItemResponse(
        id = from.id,
        name = from.name,
        description = from.description,
        isFavorite = from.isFavorite
    )
}

class CharacterItemLocalMapper : DomainMapper<CharacterItemLocal, CharacterItem> {
    override fun toDomain(from: CharacterItemLocal) = CharacterItem(
        id = from.id,
        name = from.name,
        description = from.description ?: "",
        isFavorite = from.isFavorite ?: false,
        thumbnail = from.thumbnail?.let { ThumbnailLocalMapper().toDomain(it) },
    )

    override fun toDomain(from: List<CharacterItemLocal>) = from.map { toDomain(it) }

    fun fromDomain(from: CharacterItem) = CharacterItemLocal(
        id = from.id,
        name = from.name,
        description = from.description,
        isFavorite = from.isFavorite,
        thumbnail = from.thumbnail?.let { ThumbnailLocalMapper().fromDomain(it) }
    )
}