package com.example.dailyme.presentation.screen.todayScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.io.File

@Composable
fun MealsSlotCard(
    modifier: Modifier = Modifier,
    meals: MealsTimelineItem,
    onAddClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onAddClick()
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            if (!meals.isLogged)
                Color(0xFFF3F5F7)
            else
                Color(0xFFF5F5F5)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Photo Thumbnail
//                if (meals.isLogged && !meals.photoPath.isNullOrEmpty()) {
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(File(meals.photoPath))
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = "Meal Photo",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .size(56.dp)
//                            .clip(RoundedCornerShape(12.dp))
//                    )
//                    Spacer(modifier = Modifier.width(12.dp))
//                } else if (meals.isLogged) {
//                   // Fallback for logged but no photo
//                   Box(
//                       modifier = Modifier
//                           .size(56.dp)
//                           .background(Color.LightGray.copy(0.4f), RoundedCornerShape(12.dp))
//                   )
//                   Spacer(modifier = Modifier.width(12.dp))
//                }

                Column {
                    Text(
                        text = if (meals.isLogged) {
                            "${meals.mealType} (+${meals.score})"
                        } else {
                            meals.mealType
                        },
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    if (meals.isLogged && meals.ingredient.isNotEmpty()) {
                        Text(
                            text = meals.ingredient,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            if(!meals.isLogged){
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .drawBehind {
                            drawArc(
                                color = Color.Gray,
                                startAngle = 0f,
                                sweepAngle = 360f,
                                useCenter = false,
                                style = Stroke(
                                    width = 1.dp.toPx(),
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(8f, 8f),
                                        0f
                                    )
                                )
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add ${meals.mealType}",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }


//            if (meals.isLogged) {
//                Box(
//                    modifier = Modifier
//                        .size(32.dp)
//                        .background(
//                            color = Color(0xFF0C0D10),
//                            shape = CircleShape
//                        ),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Add,
//                        contentDescription = "Add",
//                        tint = Color.White,
//                        modifier = Modifier.size(16.dp)
//                    )
//                }
//            } else {
//                Box(
//                    modifier = Modifier
//                        .size(32.dp)
//                        .drawBehind {
//                            drawArc(
//                                color = Color.Gray,
//                                startAngle = 0f,
//                                sweepAngle = 360f,
//                                useCenter = false,
//                                style = Stroke(
//                                    width = 1.dp.toPx(),
//                                    pathEffect = PathEffect.dashPathEffect(
//                                        floatArrayOf(8f, 8f),
//                                        0f
//                                    )
//                                )
//                            )
//                        },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Add,
//                        contentDescription = "Add ${meals.mealType}",
//                        tint = Color.Gray,
//                        modifier = Modifier.size(18.dp)
//                    )
//                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealsSlotCardPreview() {
    MealsSlotCard(
        meals = MealsTimelineItem(
            mealType = "Breakfast",
            isLogged = true,
            score = 8,
            ingredient = "Eggs, Toast, Coffee"
        )
    )
}
