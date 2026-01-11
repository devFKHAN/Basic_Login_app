package com.example.basicapp.data.repo

import com.example.basicapp.data.remote.ApiClient
import com.example.basicapp.data.remote.ApiService
import com.example.basicapp.domain.model.UserData
import com.example.basicapp.domain.repo.IUserDataRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteUserDataRepository @Inject constructor() : IUserDataRepository {
    override suspend fun getUserData(): Response<UserData> {
        val apiService: ApiService = ApiClient.dashboardApi
        val response = apiService.getDashboard()
        return if (response.isSuccessful) {
            val userData = response.body()?.toDomainModel()
            if (userData != null) {
                Response.success(userData)
            } else {
                Response.error(
                    response.code(),
                    response.errorBody() ?: okhttp3.ResponseBody.create(null, "")
                )
            }
        } else {
            Response.error(
                response.code(),
                response.errorBody() ?: okhttp3.ResponseBody.create(null, "")
            )
        }
    }
}