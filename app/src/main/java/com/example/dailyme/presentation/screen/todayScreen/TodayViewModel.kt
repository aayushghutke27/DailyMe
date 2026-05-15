package com.example.dailyme.presentation.screen.todayScreen

import android.health.connect.datatypes.MealType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyme.data.datastore.UserPreferenceDatastore
import com.example.dailyme.data.repository.GeminiRepository
import com.example.dailyme.data.repository.MealsRepository
import com.example.dailyme.domain.model.MealAnalysis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(

    datastore: UserPreferenceDatastore,
    private val geminiRepository: GeminiRepository,
    private val mealsRepository: MealsRepository
) : ViewModel() {

    private val today = SimpleDateFormat(
        "dd-MM-yyyy", Locale.getDefault()
    ).format(Date())

    val userName: StateFlow<String> = datastore.userName
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    val todayMeal: StateFlow<List<MealAnalysis>> =
        mealsRepository.getTodayMeals(today).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val dailyScore: StateFlow<Int> = todayMeal
        .map { meals ->
            if (meals.isEmpty()) {
                0
            } else {
                (meals.sumOf {
                    it.healthScore
                }.toFloat() / meals.size * 10).toInt()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )
    private val _dailyInsight = MutableStateFlow("Start your healthy day Add breakfast to begin tracking")
    val dailyInsight: StateFlow<String> = _dailyInsight.asStateFlow()

    init {

        viewModelScope.launch {
            todayMeal.collect { meals ->

                if (meals.isNotEmpty()) {
                    refreshDailyInsight(
                        meals,
                        dailyScore.value
                    )
                }
            }
        }
    }

    fun refreshDailyInsight(
        meals: List<MealAnalysis>,
        dailyScore: Int
    ) {

        if (meals.isEmpty()) {

            _dailyInsight.value = "Start your healthy day Add breakfast to begin tracking"
            return

        }

        viewModelScope.launch {
            try {
                val insight = geminiRepository.getDailyInsight(
                    meals = meals,
                    dailyScore = dailyScore
                )
                _dailyInsight.value = insight
            } catch (e: Exception) {
                _dailyInsight.value = "Unable to get insights right now. Please try again later."
            }
        }
    }

    fun getMealAnalysis(mealType: String): MealAnalysis? {

        return todayMeal.value
            .find {
                it.mealType == mealType
            }
            ?.takeIf {
                it.isLogged
            }
    }


}