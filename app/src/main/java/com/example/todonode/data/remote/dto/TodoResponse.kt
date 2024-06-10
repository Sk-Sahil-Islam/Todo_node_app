package com.example.todonode.data.remote.dto

data class TodoResponse(
    val msg: String,
    val success: Boolean,
    val todo: Todo
)