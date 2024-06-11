package com.example.todonode.data.remote.dto

data class TodoResponse(
    val message: String,
    val success: Boolean,
    val todo: Todo
)