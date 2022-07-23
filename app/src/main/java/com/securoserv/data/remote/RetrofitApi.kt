package com.securoserv.data.remote

import com.securoserv.data.model.ApiResponse
import com.securoserv.data.model.LoginData
import com.securoserv.data.model.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitApi {

    @POST("addUser")
    fun addUser(@Body userData: UserData): Call<ApiResponse<LoginData>>

    @GET("login")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<ApiResponse<LoginData>>
}