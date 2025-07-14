package com.test.rickandmortyapp.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anti.theft.alarm.find.feature.domain.OnboardingViewModel
import com.anti.theft.alarm.find.main.ui.MainFeatureRoot
import com.anti.theft.alarm.find.language.ui.LanguageFeatureRoot
import com.anti.theft.alarm.find.main.domain.MainViewModel
import com.anti.theft.alarm.find.more_sounds.domain.MoreSoundsViewModel
import com.anti.theft.alarm.find.more_sounds.ui.MoreSoundsFeatureRoot
import com.anti.theft.alarm.find.settings.domain.SettingsViewModel
import com.anti.theft.alarm.find.settings.ui.SettingsFeatureRoot
import com.anti.theft.alarm.find.sound.ui.SoundFeatureRoot
import com.anti.theft.alarm.find.splash.ui.SplashFeatureRoot
import com.file.photo.recovery.files.restore.feature_core.domain.RemoteConfigViewModel
import com.anti.theft.alarm.find.feature.domain.ads.AdManager
import com.anti.theft.alarm.find.onboarding.ui.OnboardingFeatureRoot
import com.anti.theft.alarm.find.sound.domain.SoundViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.json.JSONObject
import java.util.Locale

@SuppressLint("NewApi")
@Composable
fun MainNavigationRoot(
    modifier: Modifier = Modifier,
    deferredsToWait: List<Deferred<Any>>,
) {
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()

    val selectedLanguage by settingsViewModel.selectedLanguageStateFlow.collectAsState()
        NavHost(
            modifier = modifier
                .fillMaxSize(),
            navController = navController,
            startDestination = ScreenState.SPLASH,
            enterTransition = {
                fadeIn(tween(0))
            },
            exitTransition = {
                fadeOut(tween(0))
            }
        ) {
            composable(ScreenState.SPLASH) {
                selectedLanguage?.let { it1 ->
                    applySelectedLanguage(
                        activity = activity,
                        lang = it1
                    )
                }
                SplashFeatureRoot(
                    jobsToWait = remember {
                        emptyList()
                    },
                    deferredsToWait = remember {
                        listOf(
                            coroutineScope.async {
                                withTimeout(50000) {
                                    remoteConfigViewModel.isInitialized.first { it }
                                    println("ONBRD")
                                    val uri = JSONObject(
                                        remoteConfigViewModel.getConfig()
                                            .getString("ata_onboarding")
                                    ).getJSONObject("theme").run {
                                        if (true) this.getJSONObject("light").getString("uri")
                                        else this.getJSONObject("night").getString("uri")
                                    }
                                    println("ONBRD: $uri")
                                    //onboardingViewModel.initialize(uri).await()
                                }
                            }
                        ) + deferredsToWait
                    },
                    onSuccess = {
                        adManager.showAppOpenAd("app_open")
                        coroutineScope.launch {
                            if (onboardingViewModel.onboarding.first() != null) {
                                navController.navigate(ScreenState.ONBOARDING) {
                                    popUpTo(ScreenState.SPLASH) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                navController.navigate(ScreenState.MAIN) {
                                    popUpTo(ScreenState.SPLASH) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    },
                    onError = {
                        navController.navigate(ScreenState.MAIN) {
                            popUpTo(ScreenState.SPLASH) {
                                inclusive = true
                            }
                        }
                    },
                    adManager = adManager,
                )
            }
            composable(ScreenState.ONBOARDING) {
                selectedLanguage?.let { it1 ->
                    applySelectedLanguage(
                        activity = activity,
                        lang = it1
                    )
                }
                val onboardingData by onboardingViewModel.onboarding.collectAsState()
                onboardingData?.let {
                    OnboardingFeatureRoot(
                        onboarding = it,
                        adManager = adManager,
                        onSuccess = {
                            navController.navigate(ScreenState.MAIN) {
                                popUpTo(ScreenState.ONBOARDING) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
            composable(ScreenState.MAIN) {
                selectedLanguage?.let { it1 ->
                    applySelectedLanguage(
                        activity = activity,
                        lang = it1
                    )
                }
                MainFeatureRoot(
                    vm = mainViewModel,
                    settingsClicked = {
                        navController.navigate(ScreenState.SETTINGS)
                    },
                    onSoundClicked = { soundId ->
                        navController.navigate(ScreenState.SOUND + "/$soundId")
                    },
                    showMoreSounds = {
                        navController.navigate(ScreenState.MORE_SOUNDS)
                    },
                    adManager = adManager
                )
            }
            composable(ScreenState.LANGUAGE) {
                selectedLanguage?.let { it1 ->
                    applySelectedLanguage(
                        activity = activity,
                        lang = it1
                    )
                }
                LanguageFeatureRoot(
                    onBack = {
                        navController.navigateUp()
                    },
                    onOkay = {
                        navController.navigate(ScreenState.MAIN) {
                            popUpTo(ScreenState.MAIN) {
                                inclusive = true
                            }
                        }
                    },
                    adManager = adManager
                )
            }
            composable(ScreenState.SETTINGS) {
                selectedLanguage?.let { it1 ->
                    applySelectedLanguage(
                        activity = activity,
                        lang = it1
                    )
                }
                SettingsFeatureRoot(
                    vm = settingsViewModel,
                    onBack = {
                        navController.navigateUp()
                    },
                    onLangClicked = {
                        navController.navigate(ScreenState.LANGUAGE)
                    },
                    adManager = adManager
                )
            }
            composable("${ScreenState.SOUND}/{soundId}") {
                selectedLanguage?.let { it1 ->
                    applySelectedLanguage(
                        activity = activity,
                        lang = it1
                    )
                }
                val soundId = it.arguments?.getString("soundId")?.toIntOrNull() ?: 0
                SoundFeatureRoot(
                    soundId = soundId,
                    onBack = {
                        navController.navigateUp()
                    },
                    onSoundClicked = { soundId ->
                        navController.navigate(ScreenState.SOUND + "/$soundId") {
                            popUpTo(ScreenState.MAIN) {
                                inclusive = false
                            }
                        }
                    },
                    onApplied = {
                        navController.navigate(ScreenState.MAIN) {
                            popUpTo(ScreenState.MAIN) {
                                inclusive = true
                            }
                        }
                    },
                    adManager = adManager,
                    vm = soundViewModel
                )
            }
            composable(ScreenState.MORE_SOUNDS) {
                selectedLanguage?.let { it1 ->
                    applySelectedLanguage(
                        activity = activity,
                        lang = it1
                    )
                }
                MoreSoundsFeatureRoot(
                    vm = moreSoundsViewModel,
                    onBack = {
                        navController.navigateUp()
                    },
                    onSoundClicked = { soundId ->
                        navController.navigate(ScreenState.SOUND + "/$soundId")
                    },
                    adManager = adManager
                )
            }
        }
}

fun applySelectedLanguage(
    activity: ComponentActivity,
    lang: String
) {
    with(activity) {
        println("QFASA: $lang | ${resources.configuration.locales[0]}")
        resources.apply {
            val locale = Locale(lang)
            val config = Configuration(configuration)

            createConfigurationContext(configuration)
            Locale.setDefault(locale)
            config.setLocale(locale)
            resources.updateConfiguration(config, displayMetrics)
        }
    }
}
