package com.example.dailyme.presentation.screen.todayScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyme.domain.model.MealAnalysis
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TodayMealsCard(
    modifier: Modifier = Modifier,
    onAddMeal: (String) -> Unit = {},
    onViewMeal: (String) -> Unit = {},
    savedMeals: List<MealAnalysis> = emptyList()
) {

    val today = remember {
        val sdf = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        "Today, ${sdf.format(Date())}"
    }

    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }

    val allMealsTypes = listOf("Breakfast", "Lunch", "Dinner")
    val meals = allMealsTypes.map { mealType ->
        savedMeals.find { it.mealType == mealType }?.let { analysis ->
            MealsTimelineItem(
                mealType = mealType,
                isLogged = true,
                score = analysis.healthScore,
                ingredient = analysis.ingredientsDisplay,
                loggedTime = analysis.loggedTime,
                photoPath = analysis.photoPath
            )
        } ?: MealsTimelineItem(
            mealType = mealType,
            isLogged = false
        )
    }

    val breakfastTimestamp = savedMeals.find { it.mealType == "Breakfast" }?.timestamp
    val lunchTimestamp = savedMeals.find { it.mealType == "Lunch" }?.timestamp
    val dinnerTimestamp = savedMeals.find { it.mealType == "Dinner" }?.timestamp

    val mealStatus = getMealGapMessage(
        breakfastTime = breakfastTimestamp,
        lunchTime = lunchTimestamp,
        dinnerTime = dinnerTimestamp
    )


    val cardHeightPx = remember { mutableStateListOf(0, 0, 0) }



    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp)
                .navigationBarsPadding()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = today,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    val countLogged = meals.count { it.isLogged }

                    Text(
                        text = "$countLogged/3 • $mealStatus",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }

                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFF067CDC).copy(0.48f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .background(
                            color = Color(0xFF067CDC).copy(0.12f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable { onAddMeal("Breakfast") }
                        .padding(16.dp, 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Add Meals",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF067CDC)
                        )

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Meals",
                            tint = Color(0xFF067CDC),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 80.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    meals.forEachIndexed { index, meal ->
                        MealsSlotCard(
                            meals = meal,
                            onAddClick = {
                                if (meal.isLogged) {
                                    onViewMeal(meal.mealType)
                                } else {
                                    onAddMeal(meal.mealType)
                                }
                            },
                            modifier = Modifier.onGloballyPositioned { coordinates ->
                                if (index < cardHeightPx.size) {
                                    cardHeightPx[index] = coordinates.size.height
                                }
                            }
                        )

                        if (index < meals.lastIndex) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                MealsTimeline(
                    meals = meals,
                    cardHeightPx = cardHeightPx,
                    modifier = Modifier.width(48.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodayMealsCardPreview() {
    TodayMealsCard()
}

fun getMealGapMessage(
    breakfastTime: Long?,
    lunchTime: Long?,
    dinnerTime: Long?
): String {
    val currentTime = System.currentTimeMillis()

    return when {
        // Breakfast not logged
        breakfastTime == null -> {
            "Take your breakfast"
        }

        // Lunch not logged
        lunchTime == null -> {
            val diff = currentTime - breakfastTime
            val hours = diff / (1000 * 60 * 60)
            val mins = (diff / (1000 * 60)) % 60

            if (hours >= 3) {
                "Time for lunch"
            } else {
                val remainingHours = 2 - hours
                val remainingMins = 59 - mins
                "${remainingHours}h ${remainingMins}m until lunch"
            }
        }

        // Dinner not logged
        dinnerTime == null -> {
            val diff = currentTime - lunchTime
            val hours = diff / (1000 * 60 * 60)
            val mins = (diff / (1000 * 60)) % 60

            if (hours >= 8) {
                "Time for dinner"
            } else {
                val remainingHours = 7 - hours
                val remainingMins = 59 - mins
                "${remainingHours}h ${remainingMins}m until dinner"
            }
        }

        else -> {
            "All meals logged"
        }
    }
}

