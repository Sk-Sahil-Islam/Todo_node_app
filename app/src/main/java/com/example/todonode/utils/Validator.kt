package com.example.todonode.utils

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return email.matches(emailRegex.toRegex())
}

fun isValidPassword(password: String): Boolean {
    return password.length > 5
}