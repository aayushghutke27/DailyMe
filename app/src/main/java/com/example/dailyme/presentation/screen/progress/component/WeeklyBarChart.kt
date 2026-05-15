package com.example.dailyme.presentation.screen.progress.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun WeeklyBarChart(
    scores: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
) {
    val displayScores = if (scores.isEmpty()) {
        val dayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        
        // Generate last 7 days ending with "Today"
        List(7) { index ->
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, index - 6)
            val label = if (index == 6) "Today" else dayNames[cal.get(Calendar.DAY_OF_WEEK) - 1]
            label to 0
        }
    } else {
        scores
    }

    val maxScore = displayScores.maxOf { it.second }
        .coerceAtLeast(1)

    Column(modifier = modifier) {

        // Bars
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(224.dp)
        ) {
            val barCount = displayScores.size
            val totalWidth = size.width
            val slotWidth = totalWidth / barCount
            val barWidth = slotWidth * 0.6f
            val maxBarHeight = size.height * 0.84f
            val cornerRadius = 8.dp.toPx()

            displayScores.forEachIndexed { index, (label, score) ->
                val isToday = label == "Today"
                val barHeight = if (score == 0) 8.dp.toPx()
                else (score.toFloat() / maxScore) * maxBarHeight

                val left = index * slotWidth +
                        (slotWidth - barWidth) / 2f
                val top = size.height - barHeight

                if (isToday) {
                    // Dashed border bar for today
                    drawRoundRect(
                        color = Color.White.copy(alpha = 0.4f),
                        topLeft = Offset(left, top),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(
                            cornerRadius, cornerRadius
                        ),
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect
                                .dashPathEffect(
                                    floatArrayOf(8f, 4f), 0f
                                )
                        )
                    )

                    // Light fill for today
                    drawRoundRect(
                        color = Color.White.copy(alpha = 0.15f),
                        topLeft = Offset(left, top),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(
                            cornerRadius, cornerRadius
                        )
                    )
                } else if (score > 0) {
                    // Solid bar
                    drawRoundRect(
                        color = Color.White.copy(alpha = 0.35f),
                        topLeft = Offset(left, top),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(
                            cornerRadius, cornerRadius
                        )
                    )
                }

                // Score label above bar
                if (score > 0) {
                    drawContext.canvas.nativeCanvas.apply {
                        val paint = android.graphics.Paint().apply {
                            color = android.graphics.Color.WHITE
                            alpha = 180
                            textSize = 10.dp.toPx()
                            textAlign = android.graphics.Paint
                                .Align.CENTER
                        }
                        drawText(
                            "+$score",
                            left + barWidth / 2f,
                            top - 6.dp.toPx(),
                            paint
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Day labels below
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            displayScores.forEach { (label, _) ->
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = if (label == "Today")
                        Color.White
                    else
                        Color.White.copy(alpha = 0.8f),
                    fontWeight = if (label == "Today")
                        FontWeight.Bold
                    else
                        FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = (0xFF067CDC))
@Composable
fun WeeklyBarChartPreview(){
    WeeklyBarChart(
        scores = listOf(
            "Thu" to 20,
            "Fri" to 40,
            "Sat" to 60,
            "Sun" to 20,
            "Mon" to 80,
            "Tue" to 138,
            "Today" to 21
        )
    )
}