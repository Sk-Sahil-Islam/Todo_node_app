package com.example.todonode.data.remote

import com.example.todonode.data.remote.dto.LoginRequest
import com.example.todonode.data.remote.dto.LoginResponse
import com.example.todonode.data.remote.dto.RegisterRequest
import com.example.todonode.data.remote.dto.RegisterResponse
import com.example.todonode.data.remote.dto.TodoDeleteResponse
import com.example.todonode.data.remote.dto.TodoListResponse
import com.example.todonode.data.remote.dto.TodoRequest
import com.example.todonode.data.remote.dto.TodoResponse
import com.example.todonode.data.remote.dto.UserAuth
import com.example.todonode.data.remote.dto.UserAuthResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {

    @GET("api/todo/auth")
    suspend fun getUserAuth(
        @Header("Authorization") token: String
    ): UserAuthResponse

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

    @GET("api/todo/finished")
    suspend fun getFinishedTodos(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") token: String
    ): TodoListResponse

    @PUT("api/todo/{id}")
    suspend fun updateTodo(
        @Path("id") id: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body todo: TodoRequest
    ): TodoResponse

    @DELETE("api/todo/{id}")
    suspend fun deleteTod(
       @Path("id") id: String
    ): TodoDeleteResponse
}
