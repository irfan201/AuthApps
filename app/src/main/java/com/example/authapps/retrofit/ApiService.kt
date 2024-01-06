package com.example.authapps.retrofit

import com.example.authapps.model.LoginData
import com.example.authapps.response.LoginResponse
import com.example.authapps.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    @POST("api/login")
    suspend fun login(
        @Body LoginData: LoginData
    ): Response<LoginResponse>

    @GET("api/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 3
    ): Response<UserResponse>
}