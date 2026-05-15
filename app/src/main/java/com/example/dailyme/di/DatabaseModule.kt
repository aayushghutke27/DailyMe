package com.example.dailyme.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailyme.data.local.dao.MealDao
import com.example.dailyme.data.local.database.DailyMeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DailyMeDatabase {

        return Room.databaseBuilder(
            context,
            DailyMeDatabase::class.java,
            "dailyMe_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMealDao(
        database: DailyMeDatabase
    ): MealDao = database.mealDao()
}