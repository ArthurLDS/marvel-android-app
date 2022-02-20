package br.com.cwi.marvelapp.data.mapper

import br.com.cwi.marvelapp.data.model.ItemResponse
import br.com.cwi.marvelapp.domain.model.Item

class ItemMapper : DomainMapper<ItemResponse, Item> {
    override fun toDomain(from: ItemResponse) = Item(
        name = from.type,
        resourceURI = from.type,
        type = from.type,
    )

    override fun toDomain(from: List<ItemResponse>) = from.map { toDomain(it) }
}