package com.example.basicapp.domain.usercase

import com.example.basicapp.domain.repo.IAuthRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LoginUseCase @Inject constructor(val repository: IAuthRepository) {

    fun signInWithEmailAndPassword(email: String, password: String, callback: (Boolean) -> Unit) {
        repository.login(email, password, callback)
    }

    fun alreadySignedIn(): Boolean {
        return repository.checkSession()
    }

    fun signOut() {
        repository.logout()
    }

}