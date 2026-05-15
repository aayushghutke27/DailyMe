package com.example.dailyme.presentation.screen.todayScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MealsTimeline(
    modifier: Modifier = Modifier,
    meals: List<MealsTimelineItem>,
    cardHeightPx: List<Int>
) {
    val density = LocalDensity.current
    val spacerHeightDp = 8.dp
    val lineColor = Color.LightGray.copy(alpha = 0.5f)
    val dotRadius = 4.dp   // half of 8.dp dot size
    val dotRadiusLogged = 5.dp  // half of 10.dp logged dot

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        meals.forEachIndexed { index, meal ->

            val cardHeightDp = with(density) {
                cardHeightPx.getOrElse(index) { 0 }.toDp()
            }
            val currentDotRadius = if (meal.isLogged) dotRadiusLogged else dotRadius
            val prevDotRadius = if (index > 0 && meals[index - 1].isLogged) dotRadiusLogged else dotRadius

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeightDp)
                    .drawBehind {
                        val centerX = size.width / 2f
                        val centerY = size.height / 2f
                        val dotR = currentDotRadius.toPx()
                        val prevDotR = prevDotRadius.toPx()
                        val spacerPx = spacerHeightDp.toPx()
                        val gap = 6.dp.toPx() // gap between line and dot edge

                        // Line from TOP of this slot down to just above the dot
                        // (only draw top line if there's a previous card)
                        if (index > 0) {
                            val lineTop = 0f - spacerPx / 2f  // start from middle of spacer above
                            val lineBottom = centerY - dotR - gap
                            if (lineBottom > lineTop) {
                                drawLine(
                                    color = lineColor,
                                    start = Offset(centerX, lineTop),
                                    end = Offset(centerX, lineBottom),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                        }

                        // Line from just below the dot down to BOTTOM of slot
                        // (only draw bottom line if there's a next card)
                        if (index < meals.size - 1) {
                            val lineTop = centerY + dotR + gap
                            val lineBottom = size.height + spacerPx / 2f  // end at middle of spacer below
                            if (lineBottom > lineTop) {
                                drawLine(
                                    color = lineColor,
                                    start = Offset(centerX, lineTop),
                                    end = Offset(centerX, lineBottom),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (meal.isLogged && meal.loggedTime.isNotEmpty()) {
                        Text(
                            text = meal.loggedTime,
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }else{
                        // Dot
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(
                                    color = if (meal.isLogged) Color(0xFF067CDC) else Color.Transparent,
                                    shape = CircleShape
                                )
                                .border(
                                    width = 2.dp,
                                    color = if (meal.isLogged) Color.Transparent else Color.LightGray,
                                    shape = CircleShape
                                )
                        )
                    }


                }
            }

            if (index < meals.size - 1) {
                Spacer(modifier = Modifier.height(spacerHeightDp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealsTimelinePreview() {
    MealsTimeline(
        meals = listOf(
            MealsTimelineItem(mealType = "Breakfast", isLogged = true, loggedTime = "08:00"),
            MealsTimelineItem(mealType = "Lunch", isLogged = false),
            MealsTimelineItem(mealType = "Dinner", isLogged = true, loggedTime = "19:30")
        ),
        cardHeightPx = listOf(150, 100, 150)
    )
}