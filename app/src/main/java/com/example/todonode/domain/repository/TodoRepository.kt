package com.example.todonode.domain.repository

import com.example.todonode.data.remote.dto.LoginRequest
import com.example.todonode.data.remote.dto.LoginResponse
import com.example.todonode.data.remote.dto.RegisterRequest
import com.example.todonode.data.remote.dto.RegisterResponse

interface TodoRepository {
    suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse

    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse
}