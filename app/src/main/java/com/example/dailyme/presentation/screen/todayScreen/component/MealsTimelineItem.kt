package com.example.dailyme.presentation.screen.todayScreen.component

data class MealsTimelineItem(

    val mealType: String,
    val isLogged: Boolean,
    val score: Int = 0,
    val ingredient: String = "",
    val loggedTime: String = "",
    val photoPath: String? = null
)
