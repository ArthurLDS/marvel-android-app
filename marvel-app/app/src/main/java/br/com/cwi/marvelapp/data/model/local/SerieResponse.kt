package br.com.cwi.marvelapp.data.model.local

import br.com.cwi.marvelapp.data.model.remote.ThumbnailResponse

data class SerieResponse(
    val data: SerieDataResponse
)

data class SerieDataResponse(
    val results: List<ResultSerieResponse>
)

data class ResultSerieResponse(
    val description: String? = "",
    val id: String,
    val thumbnail: ThumbnailResponse,
    val title: String,
)