package com.example.dailyme.di

import android.content.Context
import com.example.dailyme.data.datastore.UserPreferenceDatastore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            // This might happen during Compose Previews if Firebase is not initialized
            // We rethrow if it's not a initialization error, or if we are not in a preview
            throw e
        }
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context,
    ): UserPreferenceDatastore =
        UserPreferenceDatastore(context)
}