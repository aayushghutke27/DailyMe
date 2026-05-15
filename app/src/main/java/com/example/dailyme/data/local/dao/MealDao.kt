package com.example.dailyme.data.local.dao

import android.health.connect.datatypes.MealType
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dailyme.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity)

    @Query(
        """SELECT * FROM meals
        WHERE date = :date"""
    )
    fun getMealsByDate(date: String): Flow<List<MealEntity>>

    @Query(
        """
        SELECT * FROM meals
        WHERE date = :date
        AND mealType = :mealType
        LIMIT 1
    """
    )
    suspend fun getMealByTypeAndDate(
        date: String,
        mealType: String
    ): MealEntity?

    @Query("SELECT * FROM meals ORDER BY date DESC")
    fun getAllMeals(): Flow<List<MealEntity>>

    @Query(
        """
            SELECT * FROM meals
            WHERE date >= :startDate
            ORDER BY date ASC
        """
    )
    fun getMealsFromDate(
        startDate: String
    ): Flow<List<MealEntity>>

}