package com.example.authapps

import com.example.authapps.response.UserResponse
import com.example.authapps.retrofit.ApiService
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    suspend fun getUsers(page: Int): Response<UserResponse> {
        return apiService.getUsers(page)
    }
}
