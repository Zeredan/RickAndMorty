package com.test.rickandmortyapp.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test.character.ui.CharacterFeatureRoot
import com.test.language.ui.LanguageFeatureRoot
import com.test.main.domain.MainViewModel
import com.test.main.ui.MainFeatureRoot
import com.test.rickandmortyapp.R
import com.test.settings.domain.SettingsViewModel
import com.test.settings.ui.SettingsFeatureRoot
import com.test.splash.ui.SplashFeatureRoot
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.json.JSONObject
import java.util.Locale

@SuppressLint("NewApi", "ContextCastToActivity")
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
    val activity = LocalContext.current as ComponentActivity

    val isDarkMode by settingsViewModel.darkModeStateFlow.collectAsState()
    NavHost(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(com.test.feature.R.color.bg_primary)),
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
                            mainViewModel.isLoading.first { !it }
                        }
                    ) + deferredsToWait
                },
                onSuccess = {
                    coroutineScope.launch {
                        navController.navigate(ScreenState.MAIN) {
                            popUpTo(ScreenState.SPLASH) {
                                inclusive = true
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
                }
            )
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
                onSettingsClicked = {
                    navController.navigate(ScreenState.SETTINGS)
                },
                onCharacterClicked = {
                    navController.navigate("${ScreenState.CHARACTER}/$it")
                }
            )
        }
        composable(
            route = "${ScreenState.CHARACTER}/{characterId}",
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")?.toIntOrNull() ?: 1
            CharacterFeatureRoot(
                id = characterId,
                onBack = {
                    navController.navigateUp()
                }
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
                isDarkMode = isDarkMode,
                onBack = {
                    navController.navigateUp()
                },
                onLangClicked = {
                    navController.navigate(ScreenState.LANGUAGE)
                }
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
                }
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
