package com.example.basicapp.domain.repo

interface IAuthRepository {

    fun login(email: String, password: String,  callback: (Boolean) -> Unit)

    fun checkSession(): Boolean

    fun logout()
}