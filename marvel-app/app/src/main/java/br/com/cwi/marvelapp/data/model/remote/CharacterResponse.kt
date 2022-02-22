package br.com.cwi.marvelapp.data.model.remote

data class CharactersResponse(
    val data: CharacterDataResponse
)

data class CharacterDataResponse(
    val results: List<CharacterItemResponse>,
    val total: Int
)

data class CharacterItemResponse(
    val id: Long,
    val name: String = "",
    val description: String = "",
    var isFavorite : Boolean = false,
    val series: SeriesResponse? = null,
    val thumbnail: ThumbnailResponse? = null,
    val comics: ComicsResponse? = null
)

data class ComicsResponse(
    val available: String = "",
    val collectionURI: String = "",
    val items: List<ItemResponse>? = null,
    val returned: String = ""
)

data class SeriesResponse(
    val available: String = "",
    val collectionURI: String = "",
    val items: List<ItemResponse>? = null,
    val returned: String = ""
)

data class ItemResponse(
    val name: String = "",
    val resourceURI: String = "",
    val type: String = ""
)

data class ThumbnailResponse(
    val extension: String = "",
    val path: String = ""
)