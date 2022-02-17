package br.com.cwi.marvelapp.data.source.remote

import br.com.cwi.marvelapp.data.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): CharactersResponse

    @GET("/v1/public/characters/{id}")
    suspend fun getCharacterDetail(
        @Path("id") id: Long
    ): CharactersResponse
}