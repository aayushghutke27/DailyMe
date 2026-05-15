package com.example.dailyme.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyme.presentation.navigation.AppNavHost
import com.example.dailyme.presentation.screen.onboardingScreen.AuthViewModel

@Composable
fun AppEntry(
    viewModel: AuthViewModel = hiltViewModel()
){
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()

   when(isLoggedIn){

       null -> {
           Box(
               modifier = Modifier
                   .fillMaxSize()
                   .background(Color.White)
           ) { }
       }
       true -> {
           AppNavHost(isLoggedIn = true)
       }
       false -> {
           AppNavHost(isLoggedIn = false)
       }
   }
}