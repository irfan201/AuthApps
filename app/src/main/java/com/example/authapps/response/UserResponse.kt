package com.example.authapps.response

import com.example.authapps.model.UserData

data class UserResponse(
    val data: List<UserData>,
    val page: Int,
    val perPage: Int,
    val totalPage: Int,
)
