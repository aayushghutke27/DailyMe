package com.example.dailyme.presentation.screen.MealsDetailedScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyme.domain.model.MealAnalysis
import com.example.dailyme.presentation.screen.MealsDetailedScreen.Components.ErrorCard
import com.example.dailyme.presentation.screen.MealsDetailedScreen.Components.HealthyScoreCard
import com.example.dailyme.presentation.screen.MealsDetailedScreen.Components.LoadingCard
import com.example.dailyme.presentation.screen.MealsDetailedScreen.Components.MacronutrientCard
import com.example.dailyme.presentation.screen.MealsDetailedScreen.Components.MealsDetailedTopBar
import com.example.dailyme.presentation.screen.MealsDetailedScreen.Components.MealsPhotoCard


@Composable
fun MealsDetailedScreen(
    mealType: String = "",
    photoPath: String? = "",
    isViewingExisting: Boolean = false,
    existingAnalysis: MealAnalysis? = null,
    onSaved: (MealAnalysis) -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: MealsDetailedViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(
        photoPath,
        mealType,
        existingAnalysis
    ) {
        when {
            isViewingExisting && existingAnalysis != null -> {
                viewModel.loadExistingMeal(existingAnalysis)
            }

            !photoPath.isNullOrEmpty() -> {
                viewModel.analyzeMeal(
                    photoPath = photoPath,
                    mealType = mealType
                )
            }
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is MealsDetailUiState.Saved) {
            onSaved((uiState as MealsDetailUiState.Saved).analysis)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()


    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xFFF3F5F7)
                )
                .systemBarsPadding()
                .padding(16.dp)
        ) {
            MealsDetailedTopBar(
                mealType = mealType,
                onBackClick = onBack
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color(0xFFF3F5F7)
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                when (val state = uiState) {

                    is MealsDetailUiState.Loading -> {
                        LoadingCard()
                        MealsPhotoCard(
                            photoPath = photoPath
                        )
                        LoadingCard()
                    }

                    is MealsDetailUiState.Success -> {

                        val analysis = state.analysis

                        HealthyScoreCard(
                            score = analysis.healthScore,
                            suggestion = analysis.suggestion,
                            mealStrength = analysis.mealStrength
                        )

                        MealsPhotoCard(
                            photoPath = analysis.photoPath,
                            ingredients = analysis.ingredients,
                            suggestion = analysis.suggestion
                        )

                        MacronutrientCard(
                            proteinLevel = analysis.proteinLevel,
                            carbsLevel = analysis.carbsLevel,
                            fatLevel = analysis.fatLevel,
                            fiberLevel = analysis.fiberLevel
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .background(
                                    color = Color(0xFF067CDC).copy(0.04f),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF067CDC).copy(0.48f),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .clickable {
                                    if (!isViewingExisting) {
                                        viewModel.saveMeal(analysis)
                                    } else {
                                        onBack()
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = if (!isViewingExisting) {
                                    "Save Meal Information"
                                } else {
                                    "Go Back"
                                },
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF067CDC)
                            )
                        }


                    }


                    is MealsDetailUiState.Error -> {

                        ErrorCard(
                            message = state.message,
                            onRetry = {
                                photoPath?.let {
                                    viewModel.retry(
                                        it, mealType
                                    )
                                }
                            }
                        )
                    }

                    else -> {}

                }


            }
        }
    }


}


@Preview(showSystemUi = true)
@Composable
fun MealsDetailedScreenPreview() {
    MealsDetailedScreen()
}