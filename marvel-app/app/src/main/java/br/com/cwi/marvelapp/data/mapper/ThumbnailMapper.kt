package br.com.cwi.marvelapp.data.mapper

import br.com.cwi.marvelapp.data.model.local.ThumbnailLocal
import br.com.cwi.marvelapp.data.model.remote.ThumbnailResponse
import br.com.cwi.marvelapp.domain.model.Thumbnail

class ThumbnailMapper : DomainMapper<ThumbnailResponse, Thumbnail> {
    override fun toDomain(from: ThumbnailResponse) = Thumbnail(
        extension = from.extension,
        path = from.path
    )

    override fun toDomain(from: List<ThumbnailResponse>) = from.map { toDomain(it) }
}

class ThumbnailLocalMapper : DomainMapper<ThumbnailLocal, Thumbnail> {
    override fun toDomain(from: ThumbnailLocal) = Thumbnail(
        extension = from.extension,
        path = from.path
    )

    override fun toDomain(from: List<ThumbnailLocal>) = from.map { toDomain(it) }

    fun fromDomain(from: Thumbnail) = ThumbnailLocal(
        extension = from.extension,
        path = from.path
    )
}