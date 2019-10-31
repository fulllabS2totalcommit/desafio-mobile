package com.felcks.desafiofulllab.repository

import com.felcks.desafiofulllab.api.IRestApi
import com.felcks.desafiofulllab.api_model.SearchRequest
import com.felcks.desafiofulllab.domain.BestInstallment
import com.felcks.desafiofulllab.domain.Product

class SearchRepository(private val api: IRestApi) {

    suspend fun getProductList(query: String?, offSet: Int, size: Int): List<Product>{

        val response = api.postResgataListagemProduto(
            SearchRequest(
                query,
                offSet,
                size)
        )

        if(!response.isSuccessful)
            throw Throwable(response.errorBody()?.string())

        if(response.body() == null)
            throw Throwable("No content")

        return response.body()?.Products!!.map {
            var bestSkusIndex: Int = 0
            var bestSellersIndex: Int = 0
            var bestPrice: Double? = null

            var skusCount = 0
            for(skus in it.Skus){
                var sellersCount = 0
                for(sellers in skus.Sellers){
                    if(bestPrice == null || sellers.Price!! < bestPrice){
                        bestSkusIndex = skusCount
                        bestSellersIndex = sellersCount
                        bestPrice = sellers.Price
                    }
                    sellersCount += 1
                }
                skusCount += 1
            }

            val bestSkus = it.Skus[bestSkusIndex]
            val bestSellers = bestSkus.Sellers[bestSellersIndex]

            Product(
                bestSkus.Name ?: "",
                bestSellers.ListPrice ?: 0.0,
                bestSellers.Price ?: 0.0,
                BestInstallment(
                    bestSellers.BestInstallment?.Count ?: 1,
                    bestSellers.BestInstallment?.Value ?: 0.0,
                    bestSellers.BestInstallment?.Total ?: 0.0,
                    bestSellers.BestInstallment?.Rate ?: 0.0
                )
            )
        }
    }
}