package com.anti.theft.alarm.find.settings.ui

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
import com.anti.theft.alarm.find.feature.R
import com.anti.theft.alarm.find.feature.domain.FireBaseViewModel
import com.anti.theft.alarm.find.feature.domain.ads.AdManager
import com.anti.theft.alarm.find.feature.domain.events.AnalyticsEvents
import com.anti.theft.alarm.find.feature.domain.events.FirebaseAnalyticsNames
import com.test.settings.domain.SettingsViewModel
import com.test.settings.ui.SettingCard

@Composable
fun SettingsFeatureRoot(
    modifier: Modifier = Modifier,
    vm: SettingsViewModel,
    fireBaseViewModel: FireBaseViewModel = hiltViewModel(),
    adManager: AdManager,
    onBack: () -> Unit,
    onLangClicked: () -> Unit
) {
    val languageMapping = mapOf(
        "en" to com.anti.theft.alarm.find.feature.R.string.lang_english,
        "de" to com.anti.theft.alarm.find.feature.R.string.lang_german,
        "es" to com.anti.theft.alarm.find.feature.R.string.lang_spanish,
        "fr" to com.anti.theft.alarm.find.feature.R.string.lang_french,
        "hi" to com.anti.theft.alarm.find.feature.R.string.lang_hindi,
        "in" to com.anti.theft.alarm.find.feature.R.string.lang_indonesian,
        "pt" to com.anti.theft.alarm.find.feature.R.string.lang_portuguese,
        "ru" to com.anti.theft.alarm.find.feature.R.string.lang_russian,
        "vi" to com.anti.theft.alarm.find.feature.R.string.lang_vietnamese,
        "zh" to com.anti.theft.alarm.find.feature.R.string.lang_chinese
    )
    val jostFontFamily = FontFamily(
        Font(
            com.anti.theft.alarm.find.feature.R.font.jost_medium,
            FontWeight.W500,
            FontStyle.Normal
        ),
        Font(
            com.anti.theft.alarm.find.feature.R.font.jost_semibold,
            FontWeight.W600,
            FontStyle.Normal
        )
    )
    val flashLightEnabled by vm.flashlightEnabledStateFlow.collectAsState()
    val vibrationEnabled by vm.vibrationEnabledStateFlow.collectAsState()
    val notificationEnabled by vm.notificationsEnabledStateFlow.collectAsState()
    val selectedLanguage by vm.selectedLanguageStateFlow.collectAsState()
    val countDownTime by vm.countdownToActivation.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(1) {
        fireBaseViewModel.sendEvent(
            FirebaseAnalyticsNames.EVENT_SCREEN,
            AnalyticsEvents.SCREEN_SETTINGS
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_primary))
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
                painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.back_arrow),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Text(
                text = stringResource(com.anti.theft.alarm.find.feature.R.string.settings),
                fontSize = 22.sp,
                color = colorResource(com.anti.theft.alarm.find.feature.R.color.dark_blue),
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
            adManager.NativeAd(
                "settings_flashlight_upper",
                false
            )
            SettingCard {
                Image(
                    painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.flash),
                    contentDescription = null
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(com.anti.theft.alarm.find.feature.R.string.flashlight),
                    fontSize = 18.sp,
                    color = colorResource(com.anti.theft.alarm.find.feature.R.color.dark_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
                Switch(
                    modifier = Modifier.size(40.dp, 24.dp),
                    checked = flashLightEnabled,
                    onCheckedChange = {
                        vm.updateFlashlightEnabled(context as ComponentActivity, it)
                        fireBaseViewModel.sendEvent(
                            FirebaseAnalyticsNames.EVENT_BUTTON_CLICK,
                            if (it) AnalyticsEvents.BUTTON_FLASH_SWITCH_ENABLE else AnalyticsEvents.BUTTON_FLASH_SWITCH_DISABLE
                        )
                        adManager.showFullScreenAd(if (it) "settings_flash_switch_enable" else "settings_flash_switch_disable")
                    },
                    thumbContent = {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(colorResource(com.anti.theft.alarm.find.feature.R.color.white))
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = colorResource(com.anti.theft.alarm.find.feature.R.color.main_blue),
                        uncheckedTrackColor = colorResource(com.anti.theft.alarm.find.feature.R.color.bg_secondary),
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent,
                        checkedThumbColor = colorResource(com.anti.theft.alarm.find.feature.R.color.white),
                        uncheckedThumbColor = colorResource(com.anti.theft.alarm.find.feature.R.color.white),
                        checkedIconColor = colorResource(com.anti.theft.alarm.find.feature.R.color.white),
                        uncheckedIconColor = colorResource(com.anti.theft.alarm.find.feature.R.color.white),
                    )
                )
            }
            adManager.NativeAd(
                "settings_flashlight_under",
                false
            )
            SettingCard{
                Image(
                    painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.vibration),
                    contentDescription = null
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(com.anti.theft.alarm.find.feature.R.string.vibration),
                    fontSize = 18.sp,
                    color = colorResource(com.anti.theft.alarm.find.feature.R.color.dark_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
                Switch(
                    modifier = Modifier.size(40.dp, 24.dp),
                    checked = vibrationEnabled,
                    onCheckedChange = {
                        vm.updateVibrationEnabled(context as ComponentActivity, it)
                        fireBaseViewModel.sendEvent(
                            FirebaseAnalyticsNames.EVENT_BUTTON_CLICK,
                            if (it) AnalyticsEvents.BUTTON_VIBRATION_SWITCH_ENABLE else AnalyticsEvents.BUTTON_VIBRATION_SWITCH_DISABLE
                        )
                        adManager.showFullScreenAd(if (it) "settings_vibration_switch_enable" else "settings_vibration_switch_disable")
                    },
                    thumbContent = {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(colorResource(com.anti.theft.alarm.find.feature.R.color.white))
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = colorResource(com.anti.theft.alarm.find.feature.R.color.main_blue),
                        uncheckedTrackColor = colorResource(com.anti.theft.alarm.find.feature.R.color.bg_secondary),
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent,
                        checkedThumbColor = colorResource(com.anti.theft.alarm.find.feature.R.color.white),
                        uncheckedThumbColor = colorResource(com.anti.theft.alarm.find.feature.R.color.white),
                        checkedIconColor = colorResource(com.anti.theft.alarm.find.feature.R.color.white),
                        uncheckedIconColor = colorResource(com.anti.theft.alarm.find.feature.R.color.white),
                    )
                )
            }
            adManager.NativeAd(
                "settings_vibration_under",
                false
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    //.height(114.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(R.color.white))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.clock),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.countdown_to_activation),
                        fontSize = 18.sp,
                        color = colorResource(R.color.dark_blue),
                        fontWeight = FontWeight.W500,
                        fontFamily = jostFontFamily,
                        lineHeight = 22.sp
                    )
                }
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    listOf(1, 3, 5, 7, 10).forEach {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .clip(RoundedCornerShape(200.dp))
                                .clickable {
                                    vm.updateCountDownTime(it)
                                    fireBaseViewModel.sendEvent(
                                        FirebaseAnalyticsNames.EVENT_BUTTON_CLICK,
                                        "${AnalyticsEvents.BUTTON_SET_COUNTDOWN_}$it"
                                    )
                                    adManager.showFullScreenAd("settings_countdown_$it")
                                }
                                .background(colorResource(if (it != countDownTime) R.color.white else R.color.bg_secondary))
                                .border(
                                    color = colorResource(if (it != countDownTime) R.color.bg_secondary else R.color.main_blue),
                                    width = 2.dp,
                                    shape = RoundedCornerShape(200.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$it${stringResource(R.string.seconds_short)}",
                                fontSize = 18.sp,
                                color = colorResource(R.color.dark_blue),
                                fontWeight = FontWeight.W500,
                                fontFamily = jostFontFamily,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }
            adManager.NativeAd(
                "settings_countdown_under",
                false
            )
            SettingCard(
                onClick = {
                    onLangClicked()
                    adManager.showFullScreenAd("settings_language")
                }
            ) {
                Image(
                    painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.language),
                    contentDescription = null
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(com.anti.theft.alarm.find.feature.R.string.language),
                    fontSize = 18.sp,
                    color = colorResource(com.anti.theft.alarm.find.feature.R.color.dark_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(
                        languageMapping[selectedLanguage]
                            ?: com.anti.theft.alarm.find.feature.R.string.lang_english
                    ),
                    fontSize = 18.sp,
                    color = colorResource(com.anti.theft.alarm.find.feature.R.color.main_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
            }
            adManager.NativeAd(
                "settings_language_under",
                false
            )
            SettingCard(
                onClick = {
                    vm.shareApp(context as ComponentActivity)
                    fireBaseViewModel.sendEvent(
                        FirebaseAnalyticsNames.EVENT_BUTTON_CLICK,
                        AnalyticsEvents.BUTTON_SETTINGS_SHARE_APP
                    )
                    adManager.showFullScreenAd("settings_share_app")
                }
            ) {
                Image(
                    painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.share),
                    contentDescription = null
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(com.anti.theft.alarm.find.feature.R.string.share),
                    fontSize = 18.sp,
                    color = colorResource(com.anti.theft.alarm.find.feature.R.color.dark_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.forward_mini_arrow),
                    contentDescription = null
                )
            }
            adManager.NativeAd(
                "settings_share_app_under",
                false
            )
            val wasAppRated by vm.wasAppRatedStateFlow.collectAsState()
            if (!wasAppRated) {
                AnimatedContent(
                    targetState = wasAppRated, label = "rate_us_fadeout",
                ) { state ->
                    if (!state) {
                        SettingCard(
                            onClick = {
                                fireBaseViewModel.sendEvent(
                                    FirebaseAnalyticsNames.EVENT_BUTTON_CLICK,
                                    AnalyticsEvents.BUTTON_SETTINGS_RATE_US
                                )
                                vm.openGooglePlayForRating(context as ComponentActivity) {
                                    fireBaseViewModel.sendEvent(
                                        FirebaseAnalyticsNames.EVENT_BUTTON_CLICK,
                                        "${AnalyticsEvents.BUTTON_RATE_}$it"
                                    )
                                }
                                adManager.showFullScreenAd("settings_rate_us")
                            }
                        ) {
                            Image(
                                painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.star),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = stringResource(com.anti.theft.alarm.find.feature.R.string.rate_us),
                                fontSize = 18.sp,
                                color = colorResource(com.anti.theft.alarm.find.feature.R.color.dark_blue),
                                fontWeight = FontWeight.W500,
                                fontFamily = jostFontFamily
                            )
                            Spacer(Modifier.weight(1f))
                            Image(
                                painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.forward_mini_arrow),
                                contentDescription = null
                            )
                        }
                    } else {

                    }
                    adManager.NativeAd(
                        "settings_rate_us_under",
                        false
                    )
                }
            }
            SettingCard(
                onClick = {
                    fireBaseViewModel.sendEvent(
                        FirebaseAnalyticsNames.EVENT_BUTTON_CLICK,
                        AnalyticsEvents.BUTTON_SETTINGS_PRIVACY_POLICY
                    )
                    vm.openCustomTab(
                        "https://telegra.ph/Privacy-Policy-for-Find-My-Phone-By-Clap-05-15",
                        context as ComponentActivity
                    )
                    adManager.showFullScreenAd("settings_privacy_policy")
                }
            ) {
                Image(
                    painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.shield), // Use appropriate icon
                    contentDescription = null
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(com.anti.theft.alarm.find.feature.R.string.privacy_policy),
                    fontSize = 18.sp,
                    color = colorResource(com.anti.theft.alarm.find.feature.R.color.dark_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.forward_mini_arrow),
                    contentDescription = null
                )
            }
            adManager.NativeAd(
                "settings_privacy_policy_under",
                false
            )
            SettingCard(
                onClick = {
                    fireBaseViewModel.sendEvent(
                        FirebaseAnalyticsNames.EVENT_BUTTON_CLICK,
                        AnalyticsEvents.BUTTON_SETTINGS_TERM_OF_USE
                    )
                    vm.openCustomTab(
                        "https://telegra.ph/Terms-of-Use-for-Find-My-Phone-By-Clap-05-15",
                        context as ComponentActivity
                    )
                    adManager.showFullScreenAd("settings_term_of_use")
                }
            ) {
                Image(
                    painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.note),
                    contentDescription = null
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(com.anti.theft.alarm.find.feature.R.string.term_of_use),
                    fontSize = 18.sp,
                    color = colorResource(com.anti.theft.alarm.find.feature.R.color.dark_blue),
                    fontWeight = FontWeight.W500,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(com.anti.theft.alarm.find.feature.R.drawable.forward_mini_arrow),
                    contentDescription = null
                )
            }
            adManager.NativeAd(
                "settings_term_of_use_under",
                false
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
