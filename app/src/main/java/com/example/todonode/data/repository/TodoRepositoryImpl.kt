package com.example.todonode.data.repository

import com.example.todonode.data.remote.TodoApi
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
import com.example.todonode.domain.repository.TodoRepository

class TodoRepositoryImpl(
    private val todoApi: TodoApi
): TodoRepository {

    override suspend fun getUserAuth(token: String): UserAuthResponse {
        return todoApi.getUserAuth(token = token)
    }

    override suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse {
        return todoApi.registerUser(registerRequest)
    }

    override suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        return todoApi.loginUser(loginRequest)
    }

    override suspend fun addTodo(token: String, todo: TodoRequest): TodoResponse {
        return todoApi.addTodo(token = token, todo = todo)
    }

    override suspend fun getTodos(token: String): TodoListResponse {
        return todoApi.getTodos(token = token)
    }

    override suspend fun getFinishedTodos(token: String): TodoListResponse {
        return todoApi.getFinishedTodos(token = token)
    }

    override suspend fun updateTodo(id: String, todo: TodoRequest): TodoResponse {
        return todoApi.updateTodo(todo = todo, id = id)
    }

    override suspend fun deleteTodo(id: String): TodoDeleteResponse {
        return todoApi.deleteTod(id = id)
    }
}