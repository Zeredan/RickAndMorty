package com.test.language.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.language.R

@Composable
fun LanguageCard(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val jostFontFamily = FontFamily(
        Font(com.test.feature.R.font.jost_medium, FontWeight.W500, FontStyle.Normal),
        Font(com.test.feature.R.font.jost_semibold, FontWeight.W600, FontStyle.Normal)
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = {
                    onClick()
                },
                indication = rememberRipple(
                    color = colorResource(com.test.feature.R.color.main_blue),
                ),
                interactionSource = remember{ MutableInteractionSource() }
            )
            .background(colorResource(com.test.feature.R.color.white))
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(3.dp))
                .size(28.dp, 20.dp),
            painter = painterResource(icon),
            contentDescription = null
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text,
            color = colorResource(com.test.feature.R.color.dark_blue),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            fontFamily = jostFontFamily
        )
        Spacer(Modifier.weight(1f))
        MarkSelector(
            selected = selected
        )
    }
}