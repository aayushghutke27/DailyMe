package com.example.dailyme.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dailyme.domain.model.MealAnalysis

@Entity(tableName = "meals")
data class MealEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mealType: String = "",
    val loggedTime: String,
    val date: String,
    val photoPath: String,
    val ingredients: String,
    val healthScore: Int,
    val suggestion: String,
    val proteinLevel: String,
    val carbsLevel: String,
    val fatLevel: String,
    val fiberLevel: String,
    val mealStrength: String
)

fun MealEntity.toDomain(): MealAnalysis = MealAnalysis(

    mealType = mealType,
    photoPath = photoPath,
    date = date,
    loggedTime = loggedTime,
    ingredients = ingredients.split(",")
        .map { it.trim() }
        .filter { it.isNotEmpty() },
    healthScore = healthScore,
    suggestion = suggestion,
    proteinLevel = proteinLevel,
    carbsLevel = carbsLevel,
    fatLevel = fatLevel,
    fiberLevel = fiberLevel,
    mealStrength = mealStrength

)

fun MealAnalysis.toEntity(): MealEntity = MealEntity (
    mealType = mealType,
    date = date,
    loggedTime = loggedTime,
    photoPath = photoPath,
    ingredients = ingredients.joinToString(","),
    healthScore = healthScore,
    suggestion = suggestion,
    proteinLevel = proteinLevel,
    carbsLevel = carbsLevel,
    fatLevel = fatLevel,
    fiberLevel = fiberLevel,
    mealStrength = mealStrength
)