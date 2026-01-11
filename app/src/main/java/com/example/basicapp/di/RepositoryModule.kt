package com.example.basicapp.di

import com.example.basicapp.data.repo.FirebaseAuthRepository
import com.example.basicapp.data.repo.RemoteUserDataRepository
import com.example.basicapp.domain.repo.IAuthRepository
import com.example.basicapp.domain.repo.IUserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindFirebaseAuthRepository(impl: FirebaseAuthRepository): IAuthRepository

    @Binds
    abstract fun bindRemoteUserDataRepository(impl: RemoteUserDataRepository): IUserDataRepository
}