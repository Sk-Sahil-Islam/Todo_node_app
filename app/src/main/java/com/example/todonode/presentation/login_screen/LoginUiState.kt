package com.example.todonode.presentation.login_screen

import com.example.todonode.data.remote.dto.LoginResponse

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val response: LoginResponse? = null
)
