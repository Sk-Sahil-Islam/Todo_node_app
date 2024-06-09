package com.example.todonode.presentation.signup_screen

import com.example.todonode.data.remote.dto.RegisterResponse

data class RegisterUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    //val isSuccess: Boolean = false
    val response: RegisterResponse? = null
)