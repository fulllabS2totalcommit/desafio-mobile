package com.example.desafio_lemobs_mobile_gabarito.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface IRestApi {

    @POST("Search/Criteria")
    suspend fun postResgataListagemProduto(): Response<List<Any>>
}