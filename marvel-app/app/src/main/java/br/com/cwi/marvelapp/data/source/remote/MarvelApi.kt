package br.com.cwi.marvelapp.data.source.remote

import br.com.cwi.marvelapp.data.model.local.SerieResponse
import br.com.cwi.marvelapp.data.model.remote.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nameStartsWith") term: String?
    ): CharactersResponse

    @GET("/v1/public/characters/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: Long
    ): CharactersResponse

    @GET("/v1/public/characters/{id}/comics")
    suspend fun getComics(
        @Path("id") id: Long
    ): SerieResponse

    @GET("/v1/public/characters/{id}/series")
    suspend fun getSeries(
        @Path("id") id: Long
    ): SerieResponse
}