package br.com.cwi.marvelapp.data.mapper

import br.com.cwi.marvelapp.data.model.ThumbnailResponse
import br.com.cwi.marvelapp.domain.model.Thumbnail

class ThumbnailMapper : DomainMapper<ThumbnailResponse, Thumbnail> {
    override fun toDomain(from: ThumbnailResponse) = Thumbnail(
        extension = from.extension,
        path = from.path
    )

    override fun toDomain(from: List<ThumbnailResponse>) = from.map { toDomain(it) }
}