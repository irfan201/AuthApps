package com.example.authapps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authapps.model.UserData
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _userList = MutableLiveData<List<UserData>>()
    val userList: LiveData<List<UserData>> get() = _userList

    fun getUsers(page: Int) {
        viewModelScope.launch {
            val response = userRepository.getUsers(page)
            if (response.isSuccessful) {
                _userList.value = response.body()?.data
            } else {
                _userList.value = emptyList()

            }
        }
    }
}