package com.example.basicapp.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicapp.domain.usercase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase) : ViewModel() {
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState>
        get() = _authState


    fun authenticateUser(email: String, password: String) {
        if (_authState.value == AuthState.CONNECTING) {
            Log.d("LoginViewModel", "authenticateUser: Already connecting, please wait.")
            return
        }
        _authState.postValue(AuthState.CONNECTING)

        loginUseCase.signInWithEmailAndPassword(email, password) { isSuccess ->

            if (isSuccess) {
                _authState.postValue(AuthState.SUCCESS)
            } else {
                _authState.postValue(AuthState.ERROR("Authentication Failed"))
            }
        }
    }

    fun isUserAlreadySignedIn(): Boolean {
        val isAlreadySignedIn = loginUseCase.alreadySignedIn()
        if (isAlreadySignedIn) {
            _authState.postValue(AuthState.SUCCESS)
            Log.d("LoginViewModel", "User is already signed in.")
        }
        return isAlreadySignedIn
    }
}
