package com.test.settings.repository

import kotlinx.coroutines.flow.Flow

import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.test.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @ApplicationContext private val context: Context
) : SettingsRepository {
    private val LANG_KEY = stringPreferencesKey("language")
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    private val WAS_APP_RATED = booleanPreferencesKey("was_app_rated")
    private val ONBOARDING_URI = stringPreferencesKey("onboarding_uri")
    private val NOTIFICATION_ENABLED_KEY = booleanPreferencesKey("notification_enabled")

    override fun getSelectedLanguageAsFlow(): Flow<String?> {
        return dataStore.data.map {
            it[LANG_KEY]
        }
    }

    override suspend fun updateSelectedLanguage(lang: String) {
        dataStore.edit {
            it[LANG_KEY] = lang
        }
    }

    override fun getDarkModeEnabledAsFlow(): Flow<Boolean?> {
        return dataStore.data.map{
            it[DARK_MODE_KEY]
        }
    }

    override suspend fun updateDarkModeEnabled(isEnabled: Boolean) {
        dataStore.edit {
            it[DARK_MODE_KEY] = isEnabled
        }
    }

    override fun getWasAppRatedAsFlow(): Flow<Boolean> {
        return dataStore.data.map {
            it[WAS_APP_RATED] ?: false
        }
    }

    override suspend fun updateWasAppRated(wasRated: Boolean) {
        dataStore.edit {
            it[WAS_APP_RATED] = wasRated
        }
    }

    override fun getOnboardingUri(): Flow<String> {
        return dataStore.data.map {
            it[ONBOARDING_URI] ?: ""
        }
    }

    override suspend fun updateOnboardingUri(uri: String) {
        dataStore.edit {
            it[ONBOARDING_URI] = uri
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun getNotificationEnabledAsFlow(): Flow<Boolean> {
        return dataStore.data.map {
            (it[NOTIFICATION_ENABLED_KEY] ?: true) &&
                    ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }
    }

    override suspend fun updateNotificationEnabled(isEnabled: Boolean) {
        dataStore.edit {
            it[NOTIFICATION_ENABLED_KEY] = isEnabled
        }
    }
}
