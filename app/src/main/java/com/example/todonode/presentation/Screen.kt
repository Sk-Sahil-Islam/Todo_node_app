package com.example.todonode.presentation

sealed class Screen(val route: String) {
    data object LoginScreen : Screen("login_screen")
    data object SignUpScreen : Screen("signup_screen")
    data object HomeScreen : Screen("home_screen")
    data object AddTodoScreen : Screen("add_todo_screen")
    data object UpdateTodoScreen : Screen("update_todo_screen")
    data object CompletedScreen : Screen("completed_screen")
}