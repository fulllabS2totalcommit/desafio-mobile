package com.felcks.desafiofulllab.common.repository

import com.felcks.desafiofulllab.api.IRestApi
import com.felcks.desafiofulllab.common.domain.Category
import com.felcks.desafiofulllab.common.domain.SubCategory

class CategoryRepository(private val api: IRestApi) {

    suspend fun getCategoryList(): List<Category>{

        val response = api.getArvoreCategoria()

        if(!response.isSuccessful)
            throw Throwable(response.errorBody()?.string())

        if(response.body() == null)
            throw Throwable("No content")

        return response.body()?.Categories!!.map {
            Category(
                it.Name ?: "",
                it.SubCategories.map {
                    SubCategory(it.Name ?: "")
                }
            )
        }
    }
}