package com.securoserv.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BaseURL = "https://securoServ.sa/api/"

    private var securoApi: RetrofitApi? = null

    init {
        initRetrofitClient()
    }

    fun getApi(): RetrofitApi {
        val retrofit = initRetrofitClient()
        securoApi = retrofit.create(RetrofitApi::class.java)
        return securoApi!!
    }

    private fun initRetrofitClient(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor {
                val requestBuilder = it.request().newBuilder()
                it.proceed(requestBuilder.build())
            }
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
}


