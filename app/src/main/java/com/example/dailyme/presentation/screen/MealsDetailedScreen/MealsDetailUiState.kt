package com.example.dailyme.presentation.screen.MealsDetailedScreen

import android.os.Message
import com.example.dailyme.domain.model.MealAnalysis

sealed class MealsDetailUiState {

    object Idle: MealsDetailUiState()
    object Loading: MealsDetailUiState()
    data class Success(
        val analysis: MealAnalysis
    ) : MealsDetailUiState()
    data class Error(
        val message: String
    ) : MealsDetailUiState()

    data class Saved(
        val analysis: MealAnalysis
    ): MealsDetailUiState()


}