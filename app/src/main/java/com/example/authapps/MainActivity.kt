package com.example.authapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.authapps.adapter.UserAdapter
import com.example.authapps.auth.LoginActivity
import com.example.authapps.databinding.ActivityMainBinding
import com.example.authapps.retrofit.ApiConfig

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: MainViewModel
    private val userAdapter = UserAdapter()
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var sessionManager: SessionManager


    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this, UserViewModelFactory(UserRepository(ApiConfig.getApiService()))).get(MainViewModel::class.java)

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        setupRecyclerView()

        userViewModel.getUsers(currentPage)

        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                        currentPage++
                        userViewModel.getUsers(currentPage)
                    }
                }
            }
        })

        userViewModel.userList.observe(this, Observer { userList ->
            userAdapter.addUsers(userList)
        })

        binding.fabLogout.setOnClickListener {
            logout()
        }
    }

    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter
    }

    private fun logout() {
        sessionManager.setLogin(false)

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
