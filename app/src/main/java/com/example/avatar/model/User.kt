package com.example.avatar.model

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String = "",
    val birthDate: String = "",
    val gender: String = ""
) {
    val fullName: String get() = "$firstName $lastName".trim()

    val initials: String
        get() = listOf(firstName, lastName)
            .filter { it.isNotBlank() }
            .map { it.first().uppercaseChar() }
            .joinToString("")
            .ifBlank { "?" }
}
