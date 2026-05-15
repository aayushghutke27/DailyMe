package com.example.dailyme.presentation.screen.SelectMethodScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onVisibilityChangedNode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectMethodScreen(
    onTextClick : () -> Unit = {},
    onImportClick : () -> Unit = {},
    onCameraClick : () -> Unit = {},
    onDismissClick: () -> Unit = {}

){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2E7DD1),
                        Color(0xFF63ABE3)
                    )
                )
            )
            .systemBarsPadding()
            .padding(
                bottom = 16.dp
            )
    ){

        Column(

            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Select Method",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Add your meal in the most convenient way",
                color = Color.White.copy(0.52f),
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.weight(1f))

            DashedOptionButton(
                label = "Text Only",
                onClick = onTextClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            DashedOptionButton(
                label = "Import Image Online",
                onClick = onImportClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            DashedOptionButton(
                label = "Click the Photo ",
                onClick = onCameraClick
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = Color.White.copy(0.16f),
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(0.20f),
                        shape = CircleShape
                    )
                    .clickable(
                        onClick = onDismissClick
                    ),
                contentAlignment = Alignment.Center,
            ) {
                
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel",
                        tint = Color.White,
                    )
                }

            }

        }


    }


}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SelectMethodScreenPreview(){

    SelectMethodScreen()

}