package com.example.todonode.data.remote

import com.example.todonode.data.remote.dto.LoginRequest
import com.example.todonode.data.remote.dto.LoginResponse
import com.example.todonode.data.remote.dto.RegisterRequest
import com.example.todonode.data.remote.dto.RegisterResponse
import com.example.todonode.data.remote.dto.TodoListResponse
import com.example.todonode.data.remote.dto.TodoRequest
import com.example.todonode.data.remote.dto.TodoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @POST("api/todo")
    suspend fun addTodo(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String,
        @Body todo: TodoRequest
    ): TodoResponse

    @GET("api/todo")
    suspend fun getTodos(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String
    ): TodoListResponse

    @PUT("api/todo/{id}")
    suspend fun updateTodo(
        @Path("id") id: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body todo: TodoRequest
    ): TodoResponse
}
