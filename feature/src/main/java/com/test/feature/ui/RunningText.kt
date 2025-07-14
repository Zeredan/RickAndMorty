package com.test.feature.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.feature.R
import kotlin.math.roundToInt

@Composable
fun RunningText(
    modifier: Modifier = Modifier,
    text: String,
    extraSpace: Float = 5f,
    textColor: Color = colorResource(R.color.dark_blue),
) {
    val jostFontFamily = FontFamily(
        Font(R.font.jost_medium, FontWeight.W500, FontStyle.Normal),
        Font(R.font.jost_semibold, FontWeight.W600, FontStyle.Normal)
    )

    var containerWidth by remember { mutableStateOf(0f) }
    var actualTextWidth by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .onSizeChanged { containerWidth = it.width.toFloat() },
        contentAlignment = Alignment.Center
    ) {
        if (containerWidth >= actualTextWidth) {
            Row(
                modifier = Modifier.horizontalScroll(
                    rememberScrollState())
            ) {
                Text(
                    text = text,
                    color = textColor,
                    fontSize = 16.sp,
                    fontFamily = jostFontFamily,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.onSizeChanged {
                        actualTextWidth = it.width.toFloat()
                    }
                )
            }
        } else {
            val totalRowWidth = (actualTextWidth * 2) + (extraSpace * LocalDensity.current.density)
            val maxOffset = totalRowWidth - actualTextWidth

            val offset by rememberInfiniteTransition("running_text_offset").animateFloat(
                label = "running_text_offset",
                initialValue = 0f,
                targetValue = maxOffset,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(
                        state = ScrollState(offset.roundToInt()),
                        enabled = false
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Text(
                        text = text,
                        color = textColor,
                        fontSize = 16.sp,
                        fontFamily = jostFontFamily,
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                    Spacer(Modifier.width(extraSpace.dp))
                    Text(
                        text = text,
                        color = textColor,
                        fontSize = 16.sp,
                        fontFamily = jostFontFamily,
                        fontWeight = FontWeight.W500,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}
