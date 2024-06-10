package com.example.todonode.presentation.add_todo_screen

import com.example.todonode.data.remote.dto.LoginResponse
import com.example.todonode.data.remote.dto.TodoResponse

data class AddTodoUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val response: TodoResponse? = null
)