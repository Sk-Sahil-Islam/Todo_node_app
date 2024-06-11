package com.example.todonode.data.remote.dto

import java.time.LocalDateTime
import java.util.Date

data class TodoRequest(
    val title: String = "",
    val description: String = "",
    val deadline: Date,
    val finished: Boolean
)
