package com.test.feature.ui

enum class UiColorTheme(

) {
    LightColorTheme(

    ),
    DarkColorTheme(

    );

    companion object{
        operator fun get(isDarkMode: Boolean) : UiColorTheme{
            return if (isDarkMode) DarkColorTheme else LightColorTheme
        }
    }
}