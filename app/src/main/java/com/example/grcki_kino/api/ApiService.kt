package com.example.grcki_kino.api

import com.example.grcki_kino.api.RetrofitBuilder.BASE_URL
import com.example.grcki_kino.data.RoundDataClass
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(BASE_URL + "draws/v3.0/{gameId}/upcoming/20")
    suspend fun getRounds(@Path("gameId") gameId: Int): List<RoundDataClass>

    @GET(BASE_URL + "draws/v3.0/{gameId}/{drawId}")
    suspend fun getRound(
        @Path("gameId") gameId: Int,
        @Path("drawId") drawId: Int
    ): RoundDataClass
}