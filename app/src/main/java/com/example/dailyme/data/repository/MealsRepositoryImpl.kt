package com.example.dailyme.data.repository

import com.example.dailyme.data.local.dao.MealDao
import com.example.dailyme.data.local.entity.toDomain
import com.example.dailyme.data.local.entity.toEntity
import com.example.dailyme.domain.model.MealAnalysis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

@Singleton
class MealsRepositoryImpl @Inject constructor(
    private val mealDao: MealDao,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : MealsRepository {
    override suspend fun saveMeal(analysis: MealAnalysis) {

        mealDao.insertMeal(analysis.toEntity())

        try {
            val userId = auth.currentUser?.uid ?: return

            firestore.collection("users")
                .document(userId)
                .collection("meals")
                .add(analysis)
                .await()
        }catch (e: Exception){

            throw Exception("User not authenticated")
        }



    }

    override fun getTodayMeals(date: String): Flow<List<MealAnalysis>> {
        return mealDao.getMealsByDate(date)
            .map { entities ->
                entities.map{
                    it.toDomain()
                }
            }
    }


    override fun getAllMeals(): Flow<List<MealAnalysis>> {
        return mealDao.getAllMeals()
            .map { entities ->
                entities.map {
                    it.toDomain()
                }
            }
    }

    override fun getMealsFromDate(startDate: String): Flow<List<MealAnalysis>> {
        return mealDao.getMealsFromDate(startDate)
            .map { entities ->
                entities.map {
                    it.toDomain()
                }
            }
    }
}
