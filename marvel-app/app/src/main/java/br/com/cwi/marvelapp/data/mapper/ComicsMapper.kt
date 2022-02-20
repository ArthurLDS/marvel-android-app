package br.com.cwi.marvelapp.data.mapper

import br.com.cwi.marvelapp.data.model.ComicsResponse
import br.com.cwi.marvelapp.domain.model.Comics

class ComicsMapper : DomainMapper<ComicsResponse, Comics> {
    override fun toDomain(from: ComicsResponse) = Comics(
        available = from.available,
        collectionURI = from.collectionURI,
        items = from.items?.let { ItemMapper().toDomain(it) },
        returned = from.returned,
    )

    override fun toDomain(from: List<ComicsResponse>) = from.map { toDomain(it) }
}