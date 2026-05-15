package com.example.dailyme.presentation.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


fun getInitials(name: String): String {
    if (name.isBlank()) return "User"
    val parts = name.trim()
        .split(" ")
        .filter { it.isNotEmpty() }
    return when {
        parts.size >= 2 ->
            "${parts[0].first().uppercaseChar()}" +
                    "${parts[1].first().uppercaseChar()}"

        parts.size == 1 ->
            "${parts[0].first().uppercaseChar()}"

        else -> "User"

    }
}

@Composable
fun UserAvatar(
    userName : String = ""
) {

    val initials = getInitials(userName)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(282.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(286.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White,
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )

        )

        Box(
            modifier = Modifier
                .size(224.dp)
                .background(
                    Color.White.copy(0.24f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center

        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(232.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF067CDC).copy(0.84f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )

            )


        }


        Text(
            text = initials,
            fontSize = 56.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )


    }
}


@Preview
@Composable
fun UserAvatarPreview() {
    UserAvatar(
        userName = "Ayush Ghutke"
    )
}