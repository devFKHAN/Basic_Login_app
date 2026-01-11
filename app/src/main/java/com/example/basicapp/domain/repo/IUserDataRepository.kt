package com.example.basicapp.domain.repo

import com.example.basicapp.domain.model.UserData
import retrofit2.Response


interface IUserDataRepository {
    suspend fun getUserData(): Response<UserData>
}