package com.example.todonode.data.remote

import com.example.todonode.data.remote.dto.LoginRequest
import com.example.todonode.data.remote.dto.LoginResponse
import com.example.todonode.data.remote.dto.RegisterRequest
import com.example.todonode.data.remote.dto.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TodoApi {

    @POST("api/todo/auth/register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): RegisterResponse

    @POST("api/todo/auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest,
        @Header("Content-Type") contentType: String = "application/json"
    ): LoginResponse
}