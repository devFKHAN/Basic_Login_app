package com.example.basicapp.presentation.login

sealed class AuthState {
    object SUCCESS : AuthState()
    object CONNECTING : AuthState()
    class ERROR(val errorMessage: String) : AuthState()
}