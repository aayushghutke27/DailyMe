package com.example.dailyme.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailyme.presentation.screen.onboardingScreen.OnboardingScreen
import com.example.dailyme.presentation.screen.mainScreen.MainScreen



@Composable
fun AppNavHost(isLoggedIn: Boolean){

    val navController = rememberNavController()

    val startDestination = if (isLoggedIn)
        Screen.MainScreen.route
    else
        Screen.Onboarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){

        composable(route = Screen.Onboarding.route){
            OnboardingScreen(
                navigateToMainScreen = { navController.navigate(Screen.MainScreen.route){
                    popUpTo(Screen.Onboarding.route){
                        inclusive = true
                    }
                } }
            )
        }

        composable(route = Screen.MainScreen.route){
            MainScreen()
        }





    }
}