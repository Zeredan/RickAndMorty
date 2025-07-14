package com.test.settings.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getWasAppRatedAsFlow() : Flow<Boolean>
    suspend fun updateWasAppRated(wasRated: Boolean)

    fun getOnboardingUri(): Flow<String>
    suspend fun updateOnboardingUri(uri: String)

    fun getSelectedLanguageAsFlow() : Flow<String?>
    suspend fun updateSelectedLanguage(lang: String)

    fun getDarkModeEnabledAsFlow() : Flow<Boolean?>
    suspend fun updateDarkModeEnabled(isEnabled: Boolean)

    fun getNotificationEnabledAsFlow() : Flow<Boolean>
    suspend fun updateNotificationEnabled(isEnabled: Boolean)
}