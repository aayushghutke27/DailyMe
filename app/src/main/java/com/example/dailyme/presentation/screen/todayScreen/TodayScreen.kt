package com.example.dailyme.presentation.screen.todayScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyme.domain.model.MealAnalysis
import com.example.dailyme.presentation.screen.todayScreen.component.DaySelectorRow
import com.example.dailyme.presentation.screen.todayScreen.component.HealthyLevelRing
import com.example.dailyme.presentation.screen.todayScreen.component.TodayHeader
import com.example.dailyme.presentation.screen.todayScreen.component.TodayMealsCard

@Composable
fun TodayScreen(
    onAddMeal: (String) -> Unit = {},
    onViewMeal: (String) -> Unit = {},
    savedMeals: List<MealAnalysis> = emptyList(),
    viewModel: TodayViewModel = hiltViewModel()
) {
    val username by viewModel.userName.collectAsStateWithLifecycle()
    val dailyInsight by viewModel.dailyInsight.collectAsStateWithLifecycle()
    val dailyScore by viewModel.dailyScore.collectAsStateWithLifecycle()
    val todayMeals by viewModel.todayMeal.collectAsStateWithLifecycle()

//    val dailyScore = if (savedMeals.isEmpty()) {
//        0
//    } else {
//        (savedMeals.sumOf { it.healthScore }.toFloat() / savedMeals.size * 10).toInt()
//    }

    LaunchedEffect(savedMeals) {
        viewModel.refreshDailyInsight(
            savedMeals,
            dailyScore
        )
    }

    TodayScreenContent(
        username = username,
        dailyScore = dailyScore,
        dailyInsight = dailyInsight,
        onAddMeal = onAddMeal,
        savedMeals = todayMeals,
        onViewMeal = onViewMeal
    )
}

@Composable
fun TodayScreenContent(
    username: String,
    dailyScore: Int = 0,
    dailyInsight: String= "Start your healthy day Add breakfast to begin tracking",
    onAddMeal: (String) -> Unit,
    onViewMeal: (String) -> Unit,
    savedMeals: List<MealAnalysis> = emptyList()

    ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF067CDC),
                        Color(0xFF63ABE3)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            TodayHeader(
                username = username
            )

            Spacer(modifier = Modifier.height(16.dp))

            HealthyLevelRing(
                score = dailyScore,
                message = dailyInsight,
                modifier = Modifier
                    .padding(horizontal = 56.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = dailyInsight,
//              fontFamily = PlayfairDisplay,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                maxLines = 3,
                lineHeight = 14.sp,
//                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(horizontal = 24.dp),

                )
            Spacer(modifier = Modifier.height(16.dp))


            DaySelectorRow()
            Spacer(modifier = Modifier.height(16.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.40f)
                .align(Alignment.BottomCenter)
        ) {
            TodayMealsCard(
                modifier = Modifier.fillMaxSize(),
                onAddMeal = onAddMeal,
                onViewMeal = onViewMeal,
                savedMeals = savedMeals
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "TodayScreen")
@Composable
fun TodayScreenPreview() {
    TodayScreenContent(
        username = "User",
        onAddMeal = {},
        onViewMeal = {},
        dailyInsight = "Start your healthy day Add breakfast to begin tracking",
        savedMeals = emptyList(),
        )
}