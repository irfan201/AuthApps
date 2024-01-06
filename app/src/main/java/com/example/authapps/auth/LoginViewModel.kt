package com.example.authapps.auth

import androidx.lifecycle.ViewModel
import com.example.authapps.SessionManager
import com.example.authapps.model.LoginData
import com.example.authapps.response.LoginResponse
import com.example.authapps.retrofit.ApiService
import retrofit2.Response

class LoginViewModel(private val sessionManager: SessionManager,private val apiService: ApiService) : ViewModel() {


    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val response = apiService.login(LoginData(email, password))

        if (response.isSuccessful) {
            val token = response.body()?.token
            sessionManager.setLogin(true)
            sessionManager.setUserToken(token ?: "")
        }

        return response
    }
}
