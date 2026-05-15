package com.example.dailyme.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface AuthRepository{

    val isLoggedIn: Flow<Boolean>
    suspend fun signInWithGoogle(context: Context): Result<UserData>
    suspend fun signOut()
}