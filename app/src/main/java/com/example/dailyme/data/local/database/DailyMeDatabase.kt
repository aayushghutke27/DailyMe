package com.example.dailyme.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dailyme.data.local.dao.MealDao
import com.example.dailyme.data.local.entity.MealEntity

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false
)

abstract class DailyMeDatabase: RoomDatabase(){
    abstract fun mealDao(): MealDao
}