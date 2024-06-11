package com.example.todonode.presentation.login_screen

import com.example.todonode.data.remote.dto.LoginResponse
import com.example.todonode.data.remote.dto.UserAuth

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val response: LoginResponse? = null,
    val user: UserAuth? = null
)
