package com.example.dailyme.presentation.screen.mainScreen

import com.example.dailyme.domain.model.MealAnalysis
import com.example.dailyme.presentation.bottomNavigationBar.BottomTab

data class MainNavigationState(

    val selectedTab: BottomTab = BottomTab.TODAY,
    val selectedMealType: String = "BREAKFAST",
    val showSelectedMethod: Boolean = false,
    val showCamera: Boolean = false,
    val showMealDetail: Boolean = false,
    val capturedPhotoPath: String? = null,
    val isViewingExistingMeal: Boolean = false,
    val existingAnalysis: MealAnalysis? = null,
//    val savedMeals: List<MealAnalysis> = emptyList()
)