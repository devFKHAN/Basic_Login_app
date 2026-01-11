package com.example.basicapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicapp.domain.model.UserData
import com.example.basicapp.domain.usercase.LoginUseCase
import com.example.basicapp.domain.usercase.FetchUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val fetchUserDataUseCase: FetchUserDataUseCase,
    val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData>
        get() = _userData


    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            val response = fetchUserDataUseCase.getUserData()
            if (response.isSuccessful) {
                response.body()?.let {
                    _userData.postValue(it)
                }
            } else {
                // Handle error case as needed
            }
        }
    }

    fun logout() {
        loginUseCase.signOut()
    }

}