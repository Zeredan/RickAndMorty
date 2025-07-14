package com.test.main.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.main.domain.MainViewModel

@Composable
fun MainFeatureRoot(
    modifier: Modifier = Modifier,
    vm: MainViewModel = hiltViewModel(),
    isDarkMode: Boolean,
    onSettingsClicked: () -> Unit,
    onCharacterClicked: (Int) -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier
            .fillMaxSize(),
        navController = navController,
        startDestination = MainScreenState.CHARACTERS,
        enterTransition = {
            fadeIn(tween(0))
        },
        exitTransition = {
            fadeOut(tween(0))
        }
    ) {
        composable(MainScreenState.CHARACTERS) {
            CharactersFeatureRoot(
                vm = vm,
                isDarkMode = isDarkMode,
                onSettingsClicked = {
                    onSettingsClicked()
                },
                onFilterClicked = {
                    navController.navigate(MainScreenState.FILTERS)
                },
                onCharacterClicked = onCharacterClicked
            )
        }
        composable(MainScreenState.FILTERS) {
            FiltersFeatureRoot(
                vm = vm,
                isDarkMode = isDarkMode,
                onBack = {
                    navController.navigateUp()
                },
                onFiltersApplied = {
                    navController.navigateUp()
                }
            )
        }
    }
}