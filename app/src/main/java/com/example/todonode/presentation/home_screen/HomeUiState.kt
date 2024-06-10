package com.example.todonode.presentation.home_screen

import com.example.todonode.data.remote.dto.TodoListResponse

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val todos: TodoListResponse? = null
)