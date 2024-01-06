package com.example.authapps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.authapps.auth.LoginViewModel
import com.example.authapps.retrofit.ApiService

class LoginViewModelFactory(private val sessionManager: SessionManager, private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(sessionManager, apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
