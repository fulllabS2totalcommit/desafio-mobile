package com.example.desafio_lemobs_mobile_gabarito.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RestApi {

    private val authInterceptor = Interceptor {chain->
        val newUrl = chain.request().url()
            .newBuilder()
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val repositorioClient = OkHttpClient().newBuilder().build()

    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://desafio.mobfiq.com.br/")
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create())
        .client(repositorioClient)
        .build()


    val api: IRestApi = retrofit().create(IRestApi::class.java)
}