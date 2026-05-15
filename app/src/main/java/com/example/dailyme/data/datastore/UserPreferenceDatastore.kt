package com.example.dailyme.data.datastore

import android.content.Context
import java.util.Locale
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date

val Context.datastore by preferencesDataStore(
    name = "user_preferences"
)

@Singleton
class UserPreferenceDatastore @Inject constructor(
    @ApplicationContext private val context: Context
) {


    val userName: Flow<String> = context.datastore.data
        .map { preferences ->
            preferences[UserPreferenceKey.USER_NAME] ?: ""
        }

    val userEmail: Flow<String> = context.datastore.data
        .map { preferences ->
            preferences[UserPreferenceKey.USER_EMAIL] ?: ""
        }

    val userPhotoUrl: Flow<String> = context.datastore.data
        .map { preferences ->
            preferences[UserPreferenceKey.USER_PHOTO_URL] ?: ""
        }

    val isLoggedIn: Flow<Boolean> = context.datastore.data
        .map { preferences ->
            preferences[UserPreferenceKey.IS_LOGGED_IN] ?: false
        }

    val isOnboardingDone: Flow<Boolean> = context.datastore.data
        .map { preferences ->
            preferences[UserPreferenceKey.IS_ONBOARDING_DONE] ?: false
        }

    val memberSince: Flow<String> = context.datastore.data
        .map { preferences ->
            preferences[UserPreferenceKey.MEMBER_SINCE] ?: ""
        }


    suspend fun saveUser(
        name: String,
        email: String,
        photoUrl: String
    ) {
        context.datastore.edit { preferences ->

            preferences[UserPreferenceKey.USER_NAME] =
                name.split(" ").firstOrNull() ?: ""
            preferences[UserPreferenceKey.USER_EMAIL] = email
            preferences[UserPreferenceKey.USER_PHOTO_URL] = photoUrl
            preferences[UserPreferenceKey.IS_LOGGED_IN] = true
            preferences[UserPreferenceKey.IS_ONBOARDING_DONE] = true

            if (!preferences.contains(
                    UserPreferenceKey.MEMBER_SINCE
                )
            ) {

                preferences[UserPreferenceKey.MEMBER_SINCE] =
                    SimpleDateFormat(
                        "dd MM yyyy", Locale.getDefault()
                    ).format(Date())
            }


        }

    }


    suspend fun clearUser() {
        context.datastore.edit { preferences ->
            preferences.clear()
        }
    }


}