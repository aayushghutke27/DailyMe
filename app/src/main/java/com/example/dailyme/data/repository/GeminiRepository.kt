package com.example.dailyme.data.repository

import com.example.dailyme.domain.model.MealAnalysis
interface GeminiRepository {

    suspend fun analyzeMealPhoto(
        photoPath : String?,
        mealType: String
    ): MealAnalysis

    suspend fun getDailyInsight(
        meals: List<MealAnalysis>,
        dailyScore: Int
    ): String
}