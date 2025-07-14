package com.test.settings.domain

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.test.settings.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    val notificationsEnabledStateFlow = settingsRepository.getNotificationEnabledAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val selectedLanguageStateFlow = settingsRepository.getSelectedLanguageAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val darkModeStateFlow = settingsRepository.getDarkModeEnabledAsFlow().map { it == true }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val wasAppRatedStateFlow = settingsRepository.getWasAppRatedAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun updateNotificationsEnabled(activity: Activity, value: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateNotificationEnabled(value)
            if (value && !settingsRepository.getNotificationEnabledAsFlow().first()) {
                activity.requestPermissions(
                    arrayOf(
                        android.Manifest.permission.POST_NOTIFICATIONS,
                    ),
                    1000
                )
            }
        }
    }

    fun updateDarkModeEnabled(value: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateDarkModeEnabled(value)
        }
    }


    fun openGooglePlayForRating(
        activity: Activity,
        onRated: (ReviewInfo) -> Unit
    ) {
        val manager = ReviewManagerFactory.create(activity)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                manager.launchReviewFlow(activity, reviewInfo)
                onRated(reviewInfo)
                viewModelScope.launch {
                    settingsRepository.updateWasAppRated(true)
                }
            } else {
                println("${task.exception} | ${task.isSuccessful} | ${task.isComplete}")
            }
        }
    }

    fun openCustomTab(uri: String, activityContext: Context) {

        val displayMetrics = activityContext.resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val halfHeight = screenHeight / 2

        val intent = CustomTabsIntent.Builder()
            .setInitialActivityHeightPx(
                halfHeight,
                CustomTabsIntent.ACTIVITY_HEIGHT_ADJUSTABLE
            )
            .build()

        val packageName = "com.android.chrome"
        val chromeInstalled = try {
            activityContext.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

        if (chromeInstalled) {
            intent.intent.setPackage(packageName)
        }
        intent.launchUrl(activityContext, Uri.parse(uri))
    }

    fun shareApp(componentActivity: ComponentActivity
    ) {
        val appName = componentActivity.getString(componentActivity.applicationInfo.labelRes)
        val playStoreUrl = "https://play.google.com/store/apps/details?id=${componentActivity.packageName}"
        val shareText = "$appName\n$playStoreUrl"
        
        val shareIntent = android.content.Intent().apply {
            action = android.content.Intent.ACTION_SEND
            type = "text/plain"
            putExtra(android.content.Intent.EXTRA_TEXT, shareText)
        }
        
        val chooserIntent = android.content.Intent.createChooser(shareIntent, "Share App")
        componentActivity.startActivity(chooserIntent)
    }


}