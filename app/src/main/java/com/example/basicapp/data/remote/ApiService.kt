package com.example.basicapp.data.remote

import com.example.basicapp.data.model.UserDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v0/b/user-contacts-ade83.appspot.com/o/student_dashboard.json")
    suspend fun getDashboard(
        @Query("alt") alt: String = "media",
        @Query("token") token: String = "0091b4c2-2ee2-4326-99cd-96d5312b34bd"
    ): Response<UserDataResponse>
}

// dummy data url:
// https://firebasestorage.googleapis.com/v0/b/user-contacts-ade83.appspot.com/o/student_dashboard.json?alt=media&token=0091b4c2-2ee2-4326-99cd-96d5312b34bd
