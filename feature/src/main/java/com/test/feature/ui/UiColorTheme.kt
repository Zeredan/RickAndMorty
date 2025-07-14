package com.test.feature.ui

import androidx.annotation.ColorRes
import com.test.feature.R

enum class UiColorTheme(
    @ColorRes val backgroundPrimary: Int,
    @ColorRes val backgroundSecondary: Int,
    @ColorRes val surfaceMain: Int,
    @ColorRes val borderInactive: Int,
    @ColorRes val containerSecondary: Int,
    @ColorRes val inputFieldBackground: Int,
    @ColorRes val textInactive: Int,
    @ColorRes val accentPrimary: Int,
    @ColorRes val accentSecondary: Int,
    @ColorRes val textPrimary: Int,
    @ColorRes val buttonSpecial: Int,
    @ColorRes val statusAlive: Int,
    @ColorRes val statusDead: Int,
    @ColorRes val dialogBg: Int,
    @ColorRes val dialogBgAlt: Int
) {
    LightColorTheme(
        backgroundPrimary = R.color.bg_primary,
        backgroundSecondary = R.color.bg_secondary,
        surfaceMain = R.color.white,
        borderInactive = R.color.border_inactive,
        containerSecondary = R.color.gray,
        inputFieldBackground = R.color.gray1,
        textInactive = R.color.text_inactive,
        accentPrimary = R.color.main_blue,
        accentSecondary = R.color.blue_secondary,
        textPrimary = R.color.dark_blue,
        buttonSpecial = R.color.purple,
        statusAlive = R.color.green,
        statusDead = R.color.red,
        dialogBg = R.color.black,
        dialogBgAlt = R.color.black1
    ),
    DarkColorTheme(
        backgroundPrimary = R.color.anti_bg_primary,
        backgroundSecondary = R.color.anti_bg_secondary,
        surfaceMain = R.color.anti_white,
        borderInactive = R.color.anti_border_inactive,
        containerSecondary = R.color.anti_gray,
        inputFieldBackground = R.color.anti_gray1,
        textInactive = R.color.anti_text_inactive,
        accentPrimary = R.color.anti_main_blue,
        accentSecondary = R.color.anti_blue_secondary,
        textPrimary = R.color.anti_dark_blue,
        buttonSpecial = R.color.anti_purple,
        statusAlive = R.color.anti_green,
        statusDead = R.color.anti_red,
        dialogBg = R.color.anti_black,
        dialogBgAlt = R.color.anti_black1
    );

    companion object{
        operator fun get(isDarkMode: Boolean) : UiColorTheme{
            return if (isDarkMode) DarkColorTheme else LightColorTheme
        }
    }
}