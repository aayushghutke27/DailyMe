package com.example.dailyme.presentation.screen.onboardingScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyme.R
import com.example.dailyme.ui.theme.PlayfairDisplay

@Composable
fun OnboardingScreen(
    navigateToMainScreen : () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    OnboardingScreenContent(
        authState = authState,
        onSignInClick = { viewModel.signInWithGoogle(context) },
        navigateToMainScreen = {
            viewModel.resetState()
            navigateToMainScreen()
        }
    )
}

@Composable
fun OnboardingScreenContent(
    authState: AuthState,
    onSignInClick: () -> Unit,
    navigateToMainScreen: () -> Unit
) {
    LaunchedEffect(authState) {
        if (authState is AuthState.Success){
            navigateToMainScreen()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),

    ){
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Let's make",
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 44.sp,
                color = Color.Black,
                lineHeight = 52.sp
            )

            Text(
                text = "your days",
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                fontSize = 44.sp,
                color = Color.Black,
                lineHeight = 52.sp
            )

            Text(
                text = "healthier",
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 44.sp,
                color = Color.Black,
                lineHeight = 52.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No Rushing. Only your feelings.",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.2.sp
            )


        }

        if (authState is AuthState.Loading){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = Color.Blue,
                strokeWidth = 2.dp
            )
        }

        if( authState is AuthState.Error){
            Text(
                text = (authState as AuthState.Error).message,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        Button(
            onClick = onSignInClick,
            enabled = authState !is AuthState.Loading,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor =  Color.Black
            )
//            border = BorderStroke(1.dp, Color.Gray)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Logo",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(32.dp)

                )

                Spacer(modifier = Modifier.width(8.dp))


                Text(
                    text = "Continue with Google",
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreenContent(
        authState = AuthState.Idle,
        onSignInClick = {},
        navigateToMainScreen = {}
    )
}