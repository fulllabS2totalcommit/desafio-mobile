package com.felcks.desafiofulllab.api

import com.felcks.desafiofulllab.api_model.categoryTree.CategoryTreeResponse
import com.felcks.desafiofulllab.api_model.search_criteria_post.SearchRequest
import com.felcks.desafiofulllab.api_model.search_criteria_post.SearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IRestApi {

    @POST("Search/Criteria")
    suspend fun postResgataListagemProduto(@Body request: SearchRequest): Response<SearchResponse>

    @GET("StorePreference/CategoryTree")
    suspend fun getArvoreCategoria(): Response<CategoryTreeResponse>
}