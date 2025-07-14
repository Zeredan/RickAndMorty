package com.test.settings.ui

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.feature.ui.UiColorTheme
import com.test.settings.R
import com.test.settings.domain.SettingsViewModel

@Composable
fun SettingsFeatureRoot(
    modifier: Modifier = Modifier,
    vm: SettingsViewModel,
    isDarkMode: Boolean,
    onBack: () -> Unit,
    onLangClicked: () -> Unit
) {
    val languageMapping = mapOf(
        "en" to com.test.feature.R.string.lang_english,
        "de" to com.test.feature.R.string.lang_german,
        "es" to com.test.feature.R.string.lang_spanish,
        "hi" to com.test.feature.R.string.lang_hindi,
        "in" to com.test.feature.R.string.lang_indonesian,
        "pt" to com.test.feature.R.string.lang_portuguese,
        "ru" to com.test.feature.R.string.lang_russian,
        "vi" to com.test.feature.R.string.lang_vietnamese,
        "zh" to com.test.feature.R.string.lang_chinese
    )
    val jostFontFamily = FontFamily(
        Font(
            com.test.feature.R.font.jost_medium,
            FontWeight.W500,
            FontStyle.Normal
        ),
        Font(
            com.test.feature.R.font.jost_semibold,
            FontWeight.W600,
            FontStyle.Normal
        )
    )
    val notificationEnabled by vm.notificationsEnabledStateFlow.collectAsState()
    val selectedLanguage by vm.selectedLanguageStateFlow.collectAsState()

    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(com.test.feature.R.color.bg_primary))
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 11.dp),
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
                painter = painterResource(com.test.feature.R.drawable.back_arrow),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Text(
                text = stringResource(com.test.feature.R.string.settings),
                fontSize = 22.sp,
                color = colorResource(com.test.feature.R.color.dark_blue),
                fontWeight = FontWeight.W600,
                fontFamily = jostFontFamily
            )
            Spacer(Modifier.weight(1f))
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp, 20.dp, 16.dp, 0.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingCard(
                onClick = {
                    onLangClicked()
                }
            ) {
                Image(
                    painter = painterResource(com.test.feature.R.drawable.language),
                    contentDescription = null
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(com.test.feature.R.string.language),
                    fontSize = 18.sp,
                    color = colorResource(com.test.feature.R.color.dark_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(
                        languageMapping[selectedLanguage]
                            ?: com.test.feature.R.string.lang_english
                    ),
                    fontSize = 18.sp,
                    color = colorResource(com.test.feature.R.color.main_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
            }
            SettingCard(
                onClick = {
                    vm.shareApp(context as ComponentActivity)
                }
            ) {
                Image(
                    painter = painterResource(com.test.feature.R.drawable.share),
                    contentDescription = null
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(com.test.feature.R.string.share),
                    fontSize = 18.sp,
                    color = colorResource(com.test.feature.R.color.dark_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(com.test.feature.R.drawable.forward_mini_arrow),
                    contentDescription = null
                )
            }
            val wasAppRated by vm.wasAppRatedStateFlow.collectAsState()
            if (!wasAppRated) {
                AnimatedContent(
                    targetState = wasAppRated, label = "rate_us_fadeout",
                ) { state ->
                    if (!state) {
                        SettingCard(
                            onClick = {
                                vm.openGooglePlayForRating(context as ComponentActivity) {
                                }
                            }
                        ) {
                            Image(
                                painter = painterResource(com.test.feature.R.drawable.star),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = stringResource(com.test.feature.R.string.rate_us),
                                fontSize = 18.sp,
                                color = colorResource(com.test.feature.R.color.dark_blue),
                                fontWeight = FontWeight.W500,
                                fontFamily = jostFontFamily
                            )
                            Spacer(Modifier.weight(1f))
                            Image(
                                painter = painterResource(com.test.feature.R.drawable.forward_mini_arrow),
                                contentDescription = null
                            )
                        }
                    } else {

                    }
                }
            }
            SettingCard {
                Image(
                    painter = painterResource(com.test.feature.R.drawable.darkmode),
                    contentDescription = null
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(com.test.feature.R.string.dark_mode),
                    fontSize = 18.sp,
                    color = colorResource(com.test.feature.R.color.dark_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
                Switch(
                    modifier = Modifier.size(40.dp, 24.dp),
                    checked = isDarkMode,
                    onCheckedChange = {
                        vm.updateDarkModeEnabled(!isDarkMode)
                    },
                    thumbContent = {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(colorResource(com.test.feature.R.color.white))
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = colorResource(com.test.feature.R.color.main_blue),
                        uncheckedTrackColor = colorResource(com.test.feature.R.color.bg_secondary),
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent,
                        checkedThumbColor = colorResource(com.test.feature.R.color.white),
                        uncheckedThumbColor = colorResource(com.test.feature.R.color.white),
                        checkedIconColor = colorResource(com.test.feature.R.color.white),
                        uncheckedIconColor = colorResource(com.test.feature.R.color.white),
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
