package com.example.dailyme.presentation.screen.SelectMethodScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashedOptionButton(
    label: String,
    onClick: () -> Unit
){

    Box(
        modifier = Modifier
            .height(52.dp)
            .drawBehind{

                drawRoundRect(
                    color = Color.White.copy(0.6f),
                    size = size,
                    cornerRadius = CornerRadius(52.dp.toPx()),
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(12f, 12f),
                            0f
                        )
                    )
                )
            }
            .clip(RoundedCornerShape(52.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ){
                onClick()
            }
            .padding(24.dp, 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.2.sp
        )
    }



}


@Preview(showBackground = true, backgroundColor =  0xFF067CDC)
@Composable
fun DashedOptionButtonPreview(){
    DashedOptionButton(
        label = "Google",
        onClick = {}
    )
}