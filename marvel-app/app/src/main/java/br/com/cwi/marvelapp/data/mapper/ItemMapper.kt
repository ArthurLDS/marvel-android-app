package br.com.cwi.marvelapp.data.mapper

import br.com.cwi.marvelapp.data.model.remote.ItemResponse
import br.com.cwi.marvelapp.domain.model.Item

class ItemMapper : DomainMapper<ItemResponse, Item> {
    override fun toDomain(from: ItemResponse) = Item(
        name = from.name,
        resourceURI = from.resourceURI,
        type = from.type,
    )

    override fun toDomain(from: List<ItemResponse>) = from.map { toDomain(it) }
}