package com.example.assignment_mob_app

data class UserAccount(
    val email: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String
)

class CredentialsManager {
    private val credentials: MutableMap<String, UserAccount> = mutableMapOf(
        "test@te.st" to UserAccount("test@te.st", "1234", "Test User", "1234567890")
    )

    fun isEmailValid(email: String): Boolean {
        if (email.isEmpty()) return false
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return emailRegex.matches(email)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

    fun isPhoneValid(phone: String): Boolean {
        return phone.isNotEmpty() && phone.all { it.isDigit() }
    }

    fun isFullNameValid(name: String): Boolean {
        return name.isNotEmpty() && name.trim().split(" ").size >= 2
    }

    fun register(email: String, password: String, fullName: String, phoneNumber: String): Result<Unit> {
        return when {
            !isEmailValid(email) -> Result.failure(IllegalArgumentException("Invalid email format"))
            !isPasswordValid(password) -> Result.failure(IllegalArgumentException("Invalid password"))
            !isFullNameValid(fullName) -> Result.failure(IllegalArgumentException("Please enter your full name"))
            !isPhoneValid(phoneNumber) -> Result.failure(IllegalArgumentException("Invalid phone number"))
            credentials.containsKey(email) -> Result.failure(IllegalArgumentException("Email already registered"))
            else -> {
                credentials[email] = UserAccount(email, password, fullName, phoneNumber)
                Result.success(Unit)
            }
        }
    }

    fun login(email: String, password: String): Boolean {
        return credentials[email]?.password == password
    }
}