package com.example.dailyme.presentation.screen.onboardingScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyme.data.repository.AuthRepository
import com.google.rpc.context.AttributeContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(

    private val authRepository: AuthRepository
) : ViewModel(){

    private val _authState = MutableStateFlow<AuthState>(
        AuthState.Idle
    )

    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    val isLoggedIn: StateFlow<Boolean?> = authRepository
        .isLoggedIn
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun signInWithGoogle(context: Context){

        viewModelScope.launch {

            _authState.value = AuthState.Loading

            val result = authRepository.signInWithGoogle(context)

            result.fold(
                onSuccess = {
                    _authState.value = AuthState.Success
                },
                onFailure = { error ->

                    _authState.value = AuthState.Error(
                        error.message ?: "Sign in failed"
                    )

                }
            )
        }
    }

    fun resetState(){
        _authState.value = AuthState.Idle
    }


}