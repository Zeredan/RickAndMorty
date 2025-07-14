package com.test.feature.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.test.feature.R
import com.test.feature.ui.UiColorTheme

@Composable
fun MarkSelector(
    modifier: Modifier = Modifier,
    afterModifier: Modifier = Modifier,
    selected: Boolean,
    isDarkMode: Boolean
) {
    if (selected) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(colorResource(UiColorTheme[isDarkMode].accentPrimary))
                .size(20.dp)
                .then(afterModifier),
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier,
                painter = painterResource(R.drawable.check_white),
                contentDescription = null
            )
        }
    }
    else{
        Box(
            modifier = modifier
                .clip(CircleShape)
                .border(
                    2.dp,
                    colorResource(UiColorTheme[isDarkMode].accentPrimary),
                    shape = CircleShape
                )
                .size(20.dp)
                .then(afterModifier)
        )
    }
}