package com.example.dailyme.presentation.screen.todayScreen.component

import android.graphics.Paint
import android.graphics.SweepGradient
import android.os.Message
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyme.ui.theme.PlayfairDisplay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HealthyLevelRing(
    score: Int,
    message: String = "Log your first meals",
    modifier: Modifier = Modifier
) {
    val animatedScore by animateIntAsState(
        targetValue = score,
        animationSpec = tween(1000),
        label = "score"
    )

    val sweepAngle by animateFloatAsState(
        targetValue = (score / 100f) * 360f,
        animationSpec = tween(1000),
        label = "sweep"
    )

    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 4.dp.toPx()
            val ringInset = strokeWidth / 2f
            val arcSize = androidx.compose.ui.geometry.Size(
                size.width - strokeWidth,
                size.height - strokeWidth
            )
            val topLeft = androidx.compose.ui.geometry.Offset(ringInset, ringInset)

            // 1. Frosted inner circle (drawn first, behind everything)
            drawCircle(
                color = Color.White.copy(alpha = 0.16f),
                radius = (size.minDimension / 2f) - strokeWidth
            )

            // 2. Background track ring
            drawArc(
                color = Color.White.copy(alpha = 0.32f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // 3. Gradient progress arc using native SweepGradient
            if (sweepAngle >= 0f) {
                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        isAntiAlias = true
                        style = Paint.Style.STROKE
                        this.strokeWidth = strokeWidth
                        strokeCap = Paint.Cap.ROUND

                        // SweepGradient always covers full 360°
                        val startColor = Color(0xFFACEFF7).copy(0.12f).toArgb()
                        val endColor = Color(0xFF78E9EF).toArgb()

                        // Fraction where the arc ends (0f to 1f)
                        val fraction = if (sweepAngle > 0f) sweepAngle / 360f else 0.0001f

                        shader = SweepGradient(
                            center.x,
                            center.y,
                            intArrayOf(
                                startColor,   // at 0° (top, -90° offset handled by rotation)
                                endColor,     // at fraction of full circle
                                Color.Transparent.toArgb(), // rest is transparent
                                Color.Transparent.toArgb()
                            ),
                            floatArrayOf(
                                0f,
                                fraction,
                                fraction + 0.001f,
                                1f
                            )
                        )
                    }

                    // Rotate canvas so 0° of the gradient aligns with -90° (top)
                    canvas.nativeCanvas.save()
                    canvas.nativeCanvas.rotate(-90f, center.x, center.y)

                    canvas.nativeCanvas.drawArc(
                        android.graphics.RectF(
                            ringInset,
                            ringInset,
                            size.width - ringInset,
                            size.height - ringInset
                        ),
                        0f,          // startAngle = 0 because we rotated canvas
                        sweepAngle.coerceAtLeast(0.01f),
                        false,
                        paint
                    )

                    canvas.nativeCanvas.restore()
                }

                // 4. White dot at END of arc
                val radius = (size.minDimension / 2f) - ringInset
                val endAngleRad = Math.toRadians((-90.0 + sweepAngle.toDouble()))
                val endX = center.x + radius * cos(endAngleRad).toFloat()
                val endY = center.y + radius * sin(endAngleRad).toFloat()

                drawCircle(
                    color = Color.White,
                    radius = strokeWidth * 1.2f,
                    center = androidx.compose.ui.geometry.Offset(endX, endY)
                )
            }
        }

        // Ring content
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )


        {
//            Text(
//                text = "Daily Health Score",
//                fontSize = 16.sp,
//                color = Color.White.copy(alpha = 0.82f)
//            )

//            Text(
//                text = "Nutritional Food",
////                fontFamily = PlayfairDisplay,
//                fontSize = 20.sp,
//                color = Color.White.copy(alpha = 0.9f),
//                fontWeight = FontWeight.Medium
//            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "$animatedScore",
                fontSize = 96.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 74.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
             Text(
                text = "Health Score",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.82f)
            )
//            Text(
//                text = message,
////              fontFamily = PlayfairDisplay,
//                fontSize = 12.sp,
//                color = Color.White.copy(alpha = 0.9f),
//                textAlign = TextAlign.Center,
//                maxLines = 3,
//                lineHeight = 12.sp,
////                fontWeight = FontWeight.Medium,
//                modifier = Modifier
//                    .padding(horizontal = 24.dp),
//
//            )

        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFF067CDC)
@Composable
fun HealthyLevelRingPreview() {
    HealthyLevelRing(
        message = "Great breakfast! Add more fiber at lunch.",
        score = 0,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    )
}
