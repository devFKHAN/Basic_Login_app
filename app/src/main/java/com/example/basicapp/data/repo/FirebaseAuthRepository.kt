package com.example.basicapp.data.repo

import android.util.Log
import com.example.basicapp.domain.repo.IAuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseAuthRepository @Inject constructor(): IAuthRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.e("FirebaseAuthRepo", "login: Email or Password is empty")
            callback(false)
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                    Log.e("FirebaseAuthRepo", "login: ${task.exception?.message}")
                }
            }
    }

    override fun checkSession(): Boolean {
        return auth.currentUser != null
    }

    override fun logout() {
        auth.signOut()
    }
}