package com.example.assignment_mob_app
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class CredentialsManagerTest {
    private lateinit var credentialsManager: CredentialsManager

    @BeforeEach
    fun setup() {
        credentialsManager = CredentialsManager()
    }

    @Test
    fun givenEmptyEmail_thenReturnFalse() {
        assertFalse(credentialsManager.isEmailValid(""))
    }

    @Test
    fun givenWrongFormatEmail_thenReturnFalse() {
        assertFalse(credentialsManager.isEmailValid("invalidemail@"))
    }

    @Test
    fun givenWellFormattedEmail_thenReturnTrue() {
        assertTrue(credentialsManager.isEmailValid("test@example.com"))
    }

    @Test
    fun givenEmptyPassword_thenReturnFalse() {
        assertFalse(credentialsManager.isPasswordValid(""))
    }

    @Test
    fun givenFilledPassword_thenReturnTrue() {
        assertTrue(credentialsManager.isPasswordValid("securePassword123"))
    }

    @Test
    fun givenValidFullName_thenReturnTrue() {
        assertTrue(credentialsManager.isFullNameValid("John Doe"))
    }

    @Test
    fun givenSingleName_thenReturnFalse() {
        assertFalse(credentialsManager.isFullNameValid("John"))
    }

    @Test
    fun givenEmptyName_thenReturnFalse() {
        assertFalse(credentialsManager.isFullNameValid(""))
    }

    @Test
    fun givenValidPhoneNumber_thenReturnTrue() {
        assertTrue(credentialsManager.isPhoneValid("1234567890"))
    }

    @Test
    fun givenInvalidPhoneNumber_thenReturnFalse() {
        assertFalse(credentialsManager.isPhoneValid("123-456-7890"))
    }

    @Test
    fun registerWithValidCredentials_shouldSucceed() {
        val result = credentialsManager.register(
            "new@example.com",
            "password123",
            "John Doe",
            "1234567890"
        )
        assertTrue(result.isSuccess)
    }

    @Test
    fun registerWithExistingEmail_shouldFail() {
        credentialsManager.register(
            "test@example.com",
            "password123",
            "John Doe",
            "1234567890"
        )
        val result = credentialsManager.register(
            "test@example.com",
            "different_password",
            "Jane Doe",
            "9876543210"
        )
        assertTrue(result.isFailure)
        assertEquals("Email already registered", result.exceptionOrNull()?.message)
    }

    @Test
    fun loginWithRegisteredCredentials_shouldSucceed() {
        val email = "login@example.com"
        val password = "password123"
        credentialsManager.register(email, password, "John Doe", "1234567890")
        assertTrue(credentialsManager.login(email, password))
    }
}