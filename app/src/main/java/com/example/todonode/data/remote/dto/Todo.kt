package com.example.todonode.data.remote.dto

data class Todo(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val deadline: String,
    val finished: Boolean,
    val title: String,
    val user: String
)