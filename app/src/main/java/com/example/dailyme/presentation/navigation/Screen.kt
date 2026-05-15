package com.example.dailyme.presentation.navigation

import okhttp3.Route

sealed class Screen(val route: String){
    object Onboarding: Screen("onboarding_screen")
    object MainScreen: Screen("main_screen")
}

