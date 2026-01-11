package com.example.basicapp.domain.usercase

import com.example.basicapp.domain.model.UserData
import com.example.basicapp.domain.repo.IUserDataRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchUserDataUseCase @Inject constructor(val userDataRepository: IUserDataRepository) {
    suspend fun getUserData() : Response<UserData> {
        return userDataRepository.getUserData()
    }

}