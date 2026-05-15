package com.example.dailyme.presentation.screen.MealsDetailedScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyme.data.repository.GeminiRepository
import com.example.dailyme.data.repository.MealsRepository
import com.example.dailyme.domain.model.MealAnalysis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MealsDetailedViewModel @Inject constructor(

    private val geminiRepository: GeminiRepository,
    private val mealsRepository: MealsRepository

): ViewModel(){

    private val _uiState = MutableStateFlow<MealsDetailUiState>(
        MealsDetailUiState.Idle
    )

    val uiState: StateFlow<MealsDetailUiState> =
        _uiState.asStateFlow()

    fun analyzeMeal(
        photoPath: String?,
        mealType: String
    ){

        viewModelScope.launch {

            _uiState.value = MealsDetailUiState.Loading

            try {

                val analysis = geminiRepository.analyzeMealPhoto(
                    photoPath = photoPath,
                    mealType = mealType
                )

                _uiState.value =
                    MealsDetailUiState.Success(analysis)

            }catch (e: Exception){

                _uiState.value = MealsDetailUiState.Error(
                    e.message ?: "Could not analyze photo. Try Again"
                )
            }
        }
    }

    fun saveMeal(analysis: MealAnalysis){
        viewModelScope.launch {
            try {
                mealsRepository.saveMeal(analysis)
                _uiState.value = MealsDetailUiState.Saved(analysis)
            } catch (e: Exception) {
                _uiState.value = MealsDetailUiState.Error(
                    e.message ?: "Failed to save meal. Check your internet or permissions."
                )
            }
        }
    }

    fun loadExistingMeal(analysis: MealAnalysis){
        _uiState.value =
            MealsDetailUiState.Success(analysis)
    }

    fun retry(photoPath: String, mealType: String){
        analyzeMeal(photoPath, mealType)
    }

}
