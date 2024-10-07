package com.example.grcki_kino.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiInstance {
    companion object {
        private const val BASE_URL: String = "https://api.opap.gr/draws/v3.0"

        private val retrofitBuilder: Retrofit.Builder =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
    }

    private val retrofit: Retrofit = retrofitBuilder.build()

    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}
