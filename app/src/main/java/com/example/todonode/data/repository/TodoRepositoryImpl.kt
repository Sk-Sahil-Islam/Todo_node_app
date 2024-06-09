package com.example.todonode.data.repository

import com.example.todonode.data.remote.TodoApi
import com.example.todonode.data.remote.dto.LoginRequest
import com.example.todonode.data.remote.dto.LoginResponse
import com.example.todonode.data.remote.dto.RegisterRequest
import com.example.todonode.data.remote.dto.RegisterResponse
import com.example.todonode.domain.repository.TodoRepository

class TodoRepositoryImpl(
    private val todoApi: TodoApi
): TodoRepository {
    override suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse {
        return todoApi.registerUser(registerRequest)
    }

    override suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        return todoApi.loginUser(loginRequest)
    }
}