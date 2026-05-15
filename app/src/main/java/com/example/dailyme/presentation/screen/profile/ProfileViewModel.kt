package com.example.dailyme.presentation.screen.profile

import android.provider.CalendarContract
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyme.data.datastore.UserPreferenceDatastore
import com.example.dailyme.data.repository.AuthRepository
import com.example.dailyme.data.repository.MealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

    private val datastore: UserPreferenceDatastore,
    private val mealsRepository: MealsRepository,
    private val authRepository: AuthRepository

): ViewModel(){

    private val today = SimpleDateFormat(
        "dd-MM-yyyy", Locale.getDefault()
    ).format(Date())

    val userName: StateFlow<String> = datastore.userName
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

    val userEmail: StateFlow<String> = datastore.userEmail
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

    val memberSince: StateFlow<String> = datastore.memberSince
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

    val totalMeals: StateFlow<Int> =
        mealsRepository.getAllMeals()
            .map {it.size}
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0
            )

    val averageScore: StateFlow<Int> =
        mealsRepository.getAllMeals()
            .map { meals ->
                if (meals.isEmpty()){
                    0
                }else{
                    meals.map {
                        it.healthScore
                    }.average().toInt()
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0
            )

    val streak: StateFlow<Int> =
        mealsRepository.getAllMeals()
            .map {meals ->
                calculateStreak(meals)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0
            )

    fun signOut(){
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    fun calculateStreak(
        meals: List<com.example.dailyme.domain.model.MealAnalysis>
    ): Int {

        if(meals.isEmpty()) return  0

        val dateFormat = SimpleDateFormat(
            "dd-MM-yyyy", Locale.getDefault()
        )

        val datesWithMeals = meals
            .map { it.date }
            .toSet()

        var streak = 0
        val calendar = Calendar.getInstance()

        while (true){
            val date = dateFormat.format(calendar.time)

            if(datesWithMeals.contains(date)){
                streak++
                calendar.add(Calendar.DAY_OF_YEAR, -1)
            }else{
                break
            }
        }
        return streak
    }
}