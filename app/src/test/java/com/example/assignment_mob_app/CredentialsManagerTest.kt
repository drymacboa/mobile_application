package com.example.assignment_mob_app
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class CredentialsManagerTest {

    @Test
    fun givenEmptyEmail_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val isEmailValid = credentialsManager.isEmailValid("")
        assertEquals(false, isEmailValid)
    }

    @Test
    fun givenWrongFormatEmail_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val isEmailValid = credentialsManager.isEmailValid("invalidemail@")
        assertEquals(false, isEmailValid)
    }

    @Test
    fun givenWellFormattedEmail_thenReturnTrue() {
        val credentialsManager = CredentialsManager()
        val isEmailValid = credentialsManager.isEmailValid("test@example.com")
        assertEquals(true, isEmailValid)
    }

    @Test
    fun givenEmptyPassword_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val isPasswordValid = credentialsManager.isPasswordValid("")
        assertEquals(false, isPasswordValid)
    }

    @Test
    fun givenFilledPassword_thenReturnTrue() {
        val credentialsManager = CredentialsManager()
        val isPasswordValid = credentialsManager.isPasswordValid("securePassword123")
        assertEquals(true, isPasswordValid)
    }
}
