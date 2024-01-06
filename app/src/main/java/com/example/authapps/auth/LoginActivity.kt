package com.example.authapps.auth

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.authapps.LoginViewModelFactory
import com.example.authapps.MainActivity
import com.example.authapps.SessionManager
import com.example.authapps.databinding.ActivityLoginBinding
import com.example.authapps.retrofit.ApiConfig
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager
    private val apiService = ApiConfig.getApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi ViewModel
        sessionManager = SessionManager(this)
        viewModel = ViewModelProvider(this, LoginViewModelFactory(sessionManager, apiService))
            .get(LoginViewModel::class.java)


        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.viewModelScope.launch {
                    val response = viewModel.login(email, password)

                    if (response.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else if (response.code() == 400) {
                        showErrorDialog("user not found")
                    }
                }
            } else {
                showErrorDialog("Email and password are required")
            }
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
