package com.example.dailyme.domain.model

data class MacroWeeklySummary(
    val proteinRatio: Float = 0f,
    val carbsRatio: Float = 0f,
    val fatRatio: Float = 0f,
    val fiberRatio: Float = 0f,
    val dominantProtein: String = "Low",
    val dominantCarbs: String = "Low",
    val dominantFat: String = "Low",
    val dominantFiber: String = "Low"
)