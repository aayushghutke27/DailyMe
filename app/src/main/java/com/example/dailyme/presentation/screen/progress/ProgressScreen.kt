package com.example.dailyme.presentation.screen.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.dailyme.domain.model.MacroWeeklySummary
import com.example.dailyme.presentation.screen.progress.component.AnalyticsPeriod
import com.example.dailyme.presentation.screen.progress.component.MacroProgressCard
import com.example.dailyme.presentation.screen.progress.component.PeriodSelector
import com.example.dailyme.presentation.screen.progress.component.WeeklyBarChart
import com.google.type.Date


@Composable
fun ProgressScreen(
    viewModel: ProgressViewModel = hiltViewModel()
) {

    val weeklyScores by viewModel.weeklyScore.collectAsStateWithLifecycle()
    val weeklyAverage by viewModel.weeklyAverage
        .collectAsStateWithLifecycle()
    val dateRangeText by viewModel.dateRangeText
        .collectAsStateWithLifecycle()
    val macroSummary by viewModel.macroSummary
        .collectAsStateWithLifecycle()

    ProgressScreenContent(
        weeklyScores = weeklyScores,
        weeklyAverage = weeklyAverage,
        dateRangeText = dateRangeText,
        macroSummary = macroSummary
    )
}

@Composable
fun ProgressScreenContent(
    weeklyScores: List<Pair<String, Int>> = emptyList(),
    weeklyAverage: Int = 0,
    dateRangeText: String = "10 - 16 May 2026",
    macroSummary: MacroWeeklySummary = MacroWeeklySummary()
) {

    var selectedPeriod by remember {
        mutableStateOf(AnalyticsPeriod.WEEK)
    }
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
                .fillMaxWidth()
                .systemBarsPadding()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {

            Text(
                text = "Analytics",
                fontSize = 26.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            PeriodSelector(
                selected = selectedPeriod,
                onSelect = { selectedPeriod = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column() {
                Text(
                    text = "WEEKLY SCORE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White.copy(alpha = 0.72f),
                    letterSpacing = 1.sp
                )

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$weeklyAverage",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "avg",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                }

                Text(
                    text = dateRangeText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.72f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            WeeklyBarChart(
                scores = weeklyScores
            )


        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.68f)
                .align(Alignment.BottomCenter)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.57f)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(
                    topStart = 42.dp,
                    topEnd = 42.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp)
                        .padding(
                            horizontal = 24.dp,
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {


                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "On track to improve",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$weeklyAverage/100 • This week",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 98f.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        MacroProgressCard(
                            label = "Protein",
                            level = macroSummary.dominantProtein,
                            ratio = macroSummary.proteinRatio
                        )
                        MacroProgressCard(
                            label = "Carbs",
                            level = macroSummary.dominantCarbs,
                            ratio = macroSummary.carbsRatio
                        )
                        MacroProgressCard(
                            label = "Fat",
                            level = macroSummary.dominantFat,
                            ratio = macroSummary.fatRatio
                        )
                        MacroProgressCard(
                            label = "Fiber",
                            level = macroSummary.dominantFiber,
                            ratio = macroSummary.fiberRatio
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "TodayScreen")
@Composable
fun ProgressScreenPreview() {
    ProgressScreenContent()
}

