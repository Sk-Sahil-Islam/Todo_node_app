package com.example.todonode.data.remote.dto

data class LoginResponse(
    val message: String,
    val success: Boolean,
    val token: String,
    val user: User
)