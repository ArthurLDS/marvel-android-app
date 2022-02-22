package br.com.cwi.marvelapp.testData

import br.com.cwi.marvelapp.data.model.local.CharacterItemLocal
import br.com.cwi.marvelapp.data.model.local.ResultSerieResponse
import br.com.cwi.marvelapp.data.model.local.SerieDataResponse
import br.com.cwi.marvelapp.data.model.local.SerieResponse
import br.com.cwi.marvelapp.data.model.remote.CharacterDataResponse
import br.com.cwi.marvelapp.data.model.remote.CharacterItemResponse
import br.com.cwi.marvelapp.data.model.remote.CharactersResponse
import br.com.cwi.marvelapp.data.model.remote.ThumbnailResponse
import br.com.cwi.marvelapp.domain.model.CharacterData
import br.com.cwi.marvelapp.domain.model.CharacterItem
import br.com.cwi.marvelapp.domain.model.Item

object TestData {

    val SERIES_RESPONSE_EMPTY_LIST = SerieResponse(
        SerieDataResponse(listOf())
    )

    val THUMBNAIL_SERIES = ThumbnailResponse(
        path = "http://i.annihil.us/u/prod/marvel/i/mg/6/40/5d41b04849a7b",
        extension = "jpg"
    )

    val CHARACTER_RESPONSE_SIMPLE_LIST = CharactersResponse(
        CharacterDataResponse(
            listOf(
                CharacterItemResponse(
                    id = 23,
                    name = "Doctor Strange"
                )
            ), total = 1
        )
    )

    val CHARACTER_LOCAL = CharacterItemLocal(
        id = 23,
        name = "Doctor Strange"
    )

    val CHARACTER_ITEM = CharacterItem(
        id = 123,
        name = "Homem de Ferro"
    )

    val CHARACTER_ITEM_FAVORITE = CharacterItem(
        id = 123,
        name = "Homem de Ferro",
        isFavorite = true
    )

    val CHARACTER_DATA = CharacterData(
        results = listOf(CHARACTER_ITEM),
        total = 20
    )

    val CHARACTER_DATA_EMPTY = CharacterData(
        results = listOf(),
        total = 0
    )

    val CHARACTER_RESPONSE_EMPTY_LIST = CharactersResponse(
        CharacterDataResponse(
            listOf(), total = 0
        )
    )

    val SERIES_RESPONSE_SIMPLE_LIST = SerieResponse(
        SerieDataResponse(
            listOf(
                ResultSerieResponse(
                    id = "123",
                    title = "Miranha longe de casa",
                    description = "Miranha no Aranha Verso",
                    thumbnail = THUMBNAIL_SERIES
                )
            )
        )
    )


    val SERIES_RESPONSE_MULTIPLE_LIST = SerieResponse(
        SerieDataResponse(
            listOf(
                ResultSerieResponse(
                    id = "123",
                    title = "Miranha longe de casa",
                    description = "Homem Aranha",
                    thumbnail = ThumbnailResponse()
                ),
                ResultSerieResponse(
                    id = "124",
                    title = "Miranha sem volta pra casa",
                    description = "Homem Aranha",
                    thumbnail = ThumbnailResponse()
                )
            )
        )
    )

    val SERIES_DATA = Item(
        name = "Homem de Ferro vol. 2",
        resourceURI = "http://i.annihil.us/u/prod/marvel/i/mg/6/40/5d41b04849a7b"
    )
}