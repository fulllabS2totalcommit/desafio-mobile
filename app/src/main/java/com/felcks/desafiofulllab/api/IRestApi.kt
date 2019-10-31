package com.felcks.desafiofulllab.api

import com.felcks.desafiofulllab.api_model.SearchRequest
import com.felcks.desafiofulllab.api_model.SearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IRestApi {

    @POST("Search/Criteria")
    suspend fun postResgataListagemProduto(@Body request: SearchRequest): Response<SearchResponse>
}