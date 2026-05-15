package com.example.dailyme.di

import com.example.dailyme.data.repository.AuthRepository
import com.example.dailyme.data.repository.AuthRepositoryImpl
import com.example.dailyme.data.repository.GeminiRepository
import com.example.dailyme.data.repository.GeminiRepositoryImpl
import com.example.dailyme.data.repository.MealsRepository
import com.example.dailyme.data.repository.MealsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGeminiRepository(
        impl: GeminiRepositoryImpl
    ): GeminiRepository

    @Binds
    @Singleton
    abstract fun bindMealsRepository(
        impl: MealsRepositoryImpl
    ): MealsRepository
}