package com.example.todonode.domain.repository

import com.example.todonode.data.remote.dto.LoginRequest
import com.example.todonode.data.remote.dto.LoginResponse
import com.example.todonode.data.remote.dto.RegisterRequest
import com.example.todonode.data.remote.dto.RegisterResponse
import com.example.todonode.data.remote.dto.TodoListResponse
import com.example.todonode.data.remote.dto.TodoRequest
import com.example.todonode.data.remote.dto.TodoResponse

interface TodoRepository {
    suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse

    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse

    suspend fun addTodo(token: String, todo: TodoRequest): TodoResponse

    suspend fun getTodos(token: String): TodoListResponse

    suspend fun updateTodo(id: String, todo: TodoRequest): TodoResponse
}