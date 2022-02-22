package br.com.cwi.marvelapp.data.mapper

import br.com.cwi.marvelapp.data.model.remote.SeriesResponse
import br.com.cwi.marvelapp.domain.model.Series

class SeriesMapper : DomainMapper<SeriesResponse, Series> {
    override fun toDomain(from: SeriesResponse) = Series(
        available = from.available,
        collectionURI = from.collectionURI,
        items = from.items?.let { ItemMapper().toDomain(it) },
        returned = from.returned
    )

    override fun toDomain(from: List<SeriesResponse>) = from.map { toDomain(it) }
}