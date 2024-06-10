package com.example.todonode.data.remote.dto

data class TodoListResponse(
    val count: Int,
    val message: String,
    val success: Boolean,
    val todos: List<Todo>
)