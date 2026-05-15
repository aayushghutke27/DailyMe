package com.example.dailyme.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferenceKey {
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val USER_PHOTO_URL = stringPreferencesKey("user_photo_url")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val IS_ONBOARDING_DONE = booleanPreferencesKey("is_onboarding_done")

    val MEMBER_SINCE = stringPreferencesKey("is_member_since")
}