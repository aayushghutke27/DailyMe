package com.example.dailyme.presentation.screen.todayScreen.component

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun DaySelectorRow() {

    val days = listOf("M", "T", "W", "T", "F", "S", "S")

    val calendar = Calendar.getInstance()

    val todayIndex = when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> 0
        Calendar.TUESDAY -> 1
        Calendar.WEDNESDAY -> 2
        Calendar.THURSDAY -> 3
        Calendar.FRIDAY -> 4
        Calendar.SATURDAY -> 5
        Calendar.SUNDAY -> 6
        else -> 0
    }

    var selectedIndex by remember { mutableStateOf(todayIndex) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEachIndexed { index, day ->

            val isSelected = index == selectedIndex
            val isPast = index < todayIndex
            val isFuture = index > todayIndex

            val circleSize = 36.dp
            val dashColor = Color.White.copy(0.32f)

            Box(
                modifier = Modifier
                    .size(circleSize)
                    .alpha(if (isFuture) 0.4f else 1f) // future days dimmed
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Color.White else Color.Transparent
                    )
                    .then(
                        if (isPast) {
                            // Dashed border for past days
                            Modifier.drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                val radius = size.minDimension / 2f - strokeWidth / 2f
                                val dashPathEffect = PathEffect.dashPathEffect(
                                    intervals = floatArrayOf(6f, 6f),
                                    phase = 0f
                                )
                                drawCircle(
                                    color = dashColor,
                                    radius = radius,
                                    center = Offset(size.width / 2f, size.height / 2f),
                                    style = Stroke(
                                        width = strokeWidth,
                                        pathEffect = dashPathEffect
                                    )
                                )
                            }
                        } else if (!isSelected) {
                            // Solid border for future/today-unselected days
                            Modifier
                                .background(
                                    color = Color.White.copy(0.12f),
                                    shape = CircleShape
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.White.copy(0.20f),
                                    shape = CircleShape
                                )
                        } else {
                            Modifier
                        }
                    )
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        selectedIndex = index
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day,
                    fontSize = 12.sp,
                    color = if (isSelected) Color.Black else Color.White.copy(0.48f)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF5B8DEF)
@Composable
fun DaySelectorPreview() {
    DaySelectorRow()
}