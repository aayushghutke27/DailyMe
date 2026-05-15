package com.example.dailyme.domain.model

data class MealAnalysis(
    val mealType: String = "",
    val photoPath: String = "",
    val date: String = "",
    val loggedTime: String = "",
    val ingredients: List<String> = emptyList(),
    val healthScore: Int = 0,
    val suggestion: String = "",
    val proteinLevel: String = "",
    val carbsLevel: String = "",
    val fatLevel: String = "",
    val fiberLevel: String = "",
    val mealStrength: String = "",
    val timestamp: Long = System.currentTimeMillis()
){

    val isLogged: Boolean
        get() =  healthScore > 0

    val ingredientsDisplay: String
        get() = ingredients.joinToString(" • ")
}

