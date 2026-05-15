package com.example.dailyme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.dailyme.presentation.AppEntry
import com.example.dailyme.presentation.screen.onboardingScreen.AuthViewModel
import com.example.dailyme.ui.theme.DailyMeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            )
        )

        val viewModel by viewModels<AuthViewModel>()

        splashScreen.setKeepOnScreenCondition{
            viewModel.isLoggedIn.value == null
        }

        setContent {
            DailyMeTheme {

                AppEntry()
            }
        }
    }
}
