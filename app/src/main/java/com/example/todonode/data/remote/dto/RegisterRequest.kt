package com.example.todonode.data.remote.dto

data class RegisterRequest(
    val email: String,
    val password: String,
    val userName: String
)