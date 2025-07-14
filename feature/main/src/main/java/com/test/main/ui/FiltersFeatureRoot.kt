package com.test.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.feature.ui.UiColorTheme
import com.test.main.domain.MainViewModel
import com.test.main.domain.filters.Gender
import com.test.main.domain.filters.Status

@Composable
fun FiltersFeatureRoot(
    modifier: Modifier = Modifier,
    vm: MainViewModel,
    onFiltersApplied: () -> Unit,
    onBack: () -> Unit,
    isDarkMode: Boolean
) {
    val jostFontFamily = FontFamily(
        Font(com.test.feature.R.font.jost_medium, FontWeight.W500),
        Font(com.test.feature.R.font.jost_semibold, FontWeight.W600)
    )
    DisposableEffect(1) {
        onDispose {
            vm.resetTemporaryFilter()
        }
    }
    val characterFilter by vm.temporaryCharacterFilter.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(11.dp, 0.dp, 11.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier
                    .padding(end = 7.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable(
                        onClick = { onBack() },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .padding(5.dp),
                painter = painterResource(com.test.feature.R.drawable.back_mini_arrow),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Text(
                text = stringResource(com.test.feature.R.string.filters),
                fontSize = 22.sp,
                color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                fontWeight = FontWeight.W600,
                fontFamily = jostFontFamily
            )
            Spacer(Modifier.weight(1f))
            Image(
                modifier = modifier
                    .clip(RoundedCornerShape(5.dp))
                    .clickable(
                        onClick = {
                            vm.applyFilter()
                            onFiltersApplied()
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .padding(5.dp),
                painter = painterResource(com.test.feature.R.drawable.ok),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(Status.ALIVE, Status.DEAD, Status.UNKNOWN).forEach { status ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            colorResource(
                                if (status == characterFilter.status)
                                    UiColorTheme[isDarkMode].accentPrimary
                                else
                                    UiColorTheme[isDarkMode].accentSecondary
                            )
                        )
                        .border(
                            width = 2.dp,
                            shape = RoundedCornerShape(12.dp),
                            color = colorResource(
                                if (status == characterFilter.status)
                                    UiColorTheme[isDarkMode].textPrimary
                                else
                                    UiColorTheme[isDarkMode].buttonSpecial
                            )
                        )
                        .clickable {
                            vm.updateTemporaryCharacterFilter(
                                characterFilter.copy(
                                    status = if (characterFilter.status != status) status else ""
                                )
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = status,
                        fontSize = 16.sp,
                        color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                        fontWeight = FontWeight.W600,
                        fontFamily = jostFontFamily
                    )
                }
            }
        }
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            value = characterFilter.species,
            onValueChange = {
                vm.updateTemporaryCharacterFilter(characterFilter.copy(species = it))
            },
            label = {
                Text(
                    text = stringResource(com.test.feature.R.string.species),
                    color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = jostFontFamily
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(UiColorTheme[isDarkMode].backgroundPrimary),
                focusedContainerColor = colorResource(UiColorTheme[isDarkMode].backgroundPrimary),
                unfocusedTextColor = colorResource(UiColorTheme[isDarkMode].textPrimary),
                focusedTextColor = colorResource(UiColorTheme[isDarkMode].textPrimary),
            )
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(Gender.MALE, Gender.FEMALE, Gender.GENDERLESS, Gender.UNKNOWN).forEach { gender ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            colorResource(
                                if (gender == characterFilter.gender)
                                    UiColorTheme[isDarkMode].accentPrimary
                                else
                                    UiColorTheme[isDarkMode].accentSecondary
                            )
                        )
                        .border(
                            width = 2.dp,
                            shape = RoundedCornerShape(12.dp),
                            color = colorResource(
                                if (gender == characterFilter.gender)
                                    UiColorTheme[isDarkMode].textPrimary
                                else
                                    UiColorTheme[isDarkMode].buttonSpecial
                            )
                        )
                        .clickable {
                            vm.updateTemporaryCharacterFilter(
                                characterFilter.copy(
                                    gender = if (characterFilter.gender != gender) gender else ""
                                )
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = gender,
                        fontSize = 16.sp,
                        color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                        fontWeight = FontWeight.W600,
                        fontFamily = jostFontFamily
                    )
                }
            }
        }
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            value = characterFilter.type,
            onValueChange = {
                vm.updateTemporaryCharacterFilter(characterFilter.copy(type = it))
            },
            label = {
                Text(
                    text = stringResource(com.test.feature.R.string.type),
                    color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = jostFontFamily
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(UiColorTheme[isDarkMode].backgroundPrimary),
                focusedContainerColor = colorResource(UiColorTheme[isDarkMode].backgroundPrimary),
                unfocusedTextColor = colorResource(UiColorTheme[isDarkMode].textPrimary),
                focusedTextColor = colorResource(UiColorTheme[isDarkMode].textPrimary),
            )
        )
    }
}