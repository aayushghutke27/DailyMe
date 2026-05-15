package com.example.dailyme.presentation.screen.progress

import java.util.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyme.data.repository.MealsRepository
import com.example.dailyme.domain.model.MacroWeeklySummary
import com.example.dailyme.domain.model.MealAnalysis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val mealsRepository: MealsRepository
) : ViewModel() {

    private val dateFormat = SimpleDateFormat(
        "dd-MM-yyyy", Locale.getDefault()
    )

    private val weekStartDate: String = run {
        val cal  = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -6)
        dateFormat.format(cal.time)
    }

    private val weeklyMeals: StateFlow<List<MealAnalysis>> =
        mealsRepository.getMealsFromDate(weekStartDate)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val weeklyScore: StateFlow<List<Pair<String, Int>>> =
        weeklyMeals.map {meals ->
            buildWeeklyScores(meals)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // Weekly average score
    val weeklyAverage: StateFlow<Int> = weeklyMeals
        .map { meals ->
            if (meals.isEmpty()) 0
            else (meals.sumOf { it.healthScore }
                .toFloat() / meals.size * 10).toInt()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

    // Date range text
    val dateRangeText: StateFlow<String> = weeklyMeals
        .map { buildDateRangeText() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            buildDateRangeText()
        )

    // Macro summary
    val macroSummary: StateFlow<MacroWeeklySummary> =
        weeklyMeals.map { meals ->
            calculateMacros(meals)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MacroWeeklySummary()
        )

    private fun buildWeeklyScores( meals: List<MealAnalysis>
    ): List<Pair<String, Int>> {
        val dayLabels = listOf(
            "Mon", "Tue", "Wed",
            "Thu", "Fri", "Sat", "Sun"
        )
        val result = mutableListOf<Pair<String, Int>>()

        val calendar = Calendar.getInstance()
        // Start from 6 days ago
        calendar.add(Calendar.DAY_OF_YEAR, -6)

        for (i in 0..6) {
            val date = dateFormat.format(calendar.time)
            val dayMeals = meals.filter { it.date == date }
            val score = if (dayMeals.isEmpty()) 0
            else (dayMeals.sumOf { it.healthScore }
                .toFloat() / dayMeals.size * 10).toInt()

            val label = if (i == 6) "Today"
            else dayLabels[
                calendar.get(Calendar.DAY_OF_WEEK).let {
                    when (it) {
                        Calendar.MONDAY -> 0
                        Calendar.TUESDAY -> 1
                        Calendar.WEDNESDAY -> 2
                        Calendar.THURSDAY -> 3
                        Calendar.FRIDAY -> 4
                        Calendar.SATURDAY -> 5
                        else -> 6 // Sunday
                    }
                }
            ]

            result.add(label to score)
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return result

    }

    private fun buildDateRangeText() : String {
        val cal = Calendar.getInstance()
        val endDate = SimpleDateFormat(
            "d MMM yyyy", Locale.getDefault()
        ).format(cal.time)
        cal.add(Calendar.DAY_OF_YEAR, -6)
        val startDate = SimpleDateFormat(
            "d", Locale.getDefault()
        ).format(cal.time)
        val month = SimpleDateFormat(
            "MMM yyyy", Locale.getDefault()
        ).format(Calendar.getInstance().time)
        return "$startDate - $endDate"

    }

    private fun calculateMacros(
        meals: List<MealAnalysis>
    ): MacroWeeklySummary {
        if (meals.isEmpty()) return MacroWeeklySummary()

        val total = meals.size.toFloat()

        fun ratio(level: String) =
            meals.count {
                it.proteinLevel.equals(level, true)
            } / total

        val proteinHigh = meals.count {
            it.proteinLevel.equals("High", true)
        } / total
        val carbsLow = meals.count {
            it.carbsLevel.equals("Low", true)
        } / total
        val fatLow = meals.count {
            it.fatLevel.equals("Low", true)
        } / total
        val fiberHigh = meals.count {
            it.fiberLevel.equals("High", true)
        } / total

        fun dominant(
            meals: List<MealAnalysis>,
            selector: (MealAnalysis) -> String
        ): String {
            return meals
                .groupBy { selector(it) }
                .maxByOrNull { it.value.size }
                ?.key ?: "Low"
        }

        return MacroWeeklySummary(
            proteinRatio = proteinHigh,
            carbsRatio = 1f - carbsLow,
            fatRatio = 1f - fatLow,
            fiberRatio = fiberHigh,
            dominantProtein = dominant(meals) {
                it.proteinLevel
            },
            dominantCarbs = dominant(meals) {
                it.carbsLevel
            },
            dominantFat = dominant(meals) {
                it.fatLevel
            },
            dominantFiber = dominant(meals) {
                it.fiberLevel
            }
        )
    }

}