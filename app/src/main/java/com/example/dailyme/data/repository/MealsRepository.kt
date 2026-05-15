package com.example.dailyme.data.repository

import android.health.connect.datatypes.MealType
import com.example.dailyme.domain.model.MealAnalysis
import kotlinx.coroutines.flow.Flow

interface MealsRepository {

    suspend fun saveMeal(analysis: MealAnalysis)

    fun getTodayMeals(
        data: String
    ) : Flow<List<MealAnalysis>>


    fun getAllMeals(): Flow<List<MealAnalysis>>


    fun getMealsFromDate(
        startDate: String
    ): Flow<List<MealAnalysis>>

//    fun getTotalScore(
//        data: String
//    ): Flow<Int>
//
//    suspend fun getMeal(
//        mealType: MealType,
//        data: String
//    ): MealAnalysis?
}