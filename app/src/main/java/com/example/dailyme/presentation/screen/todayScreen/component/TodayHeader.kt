package com.example.dailyme.presentation.screen.todayScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyme.ui.theme.PlayfairDisplay

@Composable
fun TodayHeader(
    username: String = "username"
){

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.Start

    ) {

        Text(
            text = "Hello, $username",
//            fontFamily = PlayfairDisplay,
            fontWeight = FontWeight.Medium,
            fontSize = 34.sp,
            color = Color.White,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "You're on track to...",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.White,
            lineHeight = 16.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HeaderPreview(){
    TodayHeader()
}