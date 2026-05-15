package com.example.dailyme.presentation.screen.progress.component

import android.R
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import javax.crypto.Mac

@Composable
fun MacroProgressCard(
    label: String,
    level: String,
    ratio: Float
) {
    val animatedRatio by animateFloatAsState(
        targetValue = ratio,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing

        ),
        label = "ratio"
    )

    val percentage = (ratio * 100).toInt()

    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .border(
//                width = 1.dp,
//                color = Color.LightGray.copy(0.64f),
//                shape = RoundedCornerShape(24.dp)
//
//            )
            .clip(RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor =  Color(0xFFF3F5F7)
        ),
        elevation = CardDefaults.cardElevation(0.dp)

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Text(
                    text = "$level ($percentage%)",
                    fontSize = 14.sp,
                    color = Color.Black
                )

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF63ABE3).copy(0.24f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedRatio)
                        .height(8.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF067CDC),
                                    Color(0xFF63ABE3)
                                )
                            )
                        )
                )
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun MacroProgressCardPreview() {
    MacroProgressCard(
        label = "Protein",
        level = "High",
        ratio = 50.00F
    )
}