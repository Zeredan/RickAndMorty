package com.test.language.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.feature.ui.UiColorTheme
import com.test.language.domain.LanguageViewModel
import com.test.language.model.LanguageModel

@Composable
fun LanguageFeatureRoot(
    modifier: Modifier = Modifier,
    vm: LanguageViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onOkay: () -> Unit,
    isDarkMode: Boolean
) {
    val showOkMark by vm.showOkayMark.collectAsState()
    val langData = listOf(
        LanguageModel(
            "en",
            stringResource(com.test.feature.R.string.lang_english),
            com.test.feature.R.drawable.ic_flag_en
        ),
        LanguageModel(
            "ru",
            stringResource(com.test.feature.R.string.lang_russian),
            com.test.feature.R.drawable.ic_flag_ru
        ),
        LanguageModel(
            "vi",
            stringResource(com.test.feature.R.string.lang_vietnamese),
            com.test.feature.R.drawable.ic_flag_vi
        ),
        LanguageModel(
            "hi",
            stringResource(com.test.feature.R.string.lang_hindi),
            com.test.feature.R.drawable.ic_flag_hi
        ),
        LanguageModel(
            "de",
            stringResource(com.test.feature.R.string.lang_german),
            com.test.feature.R.drawable.ic_flag_de
        ),
        LanguageModel(
            "in",
            stringResource(com.test.feature.R.string.lang_indonesian),
             com.test.feature.R.drawable.ic_flag_in
        ),
        LanguageModel(
            "pt",
            stringResource(com.test.feature.R.string.lang_portuguese),
             com.test.feature.R.drawable.ic_flag_pt
        ),
        LanguageModel(
            "zh",
            stringResource(com.test.feature.R.string.lang_chinese),
             com.test.feature.R.drawable.ic_flag_zh
        ),
        LanguageModel(
            "es",
            stringResource(com.test.feature.R.string.lang_spanish),
            com.test.feature.R.drawable.ic_flag_es
        ),
        LanguageModel(
            "az",
            stringResource(com.test.feature.R.string.lang_azerbaijani),
            com.test.feature.R.drawable.ic_flag_az
        ),
        LanguageModel(
            "bn",
            stringResource(com.test.feature.R.string.lang_bengali),
            com.test.feature.R.drawable.ic_flag_bn
        ),
        LanguageModel(
            "ka",
            stringResource(com.test.feature.R.string.lang_georgian),
            com.test.feature.R.drawable.ic_flag_ge
        ),
        LanguageModel(
            "km",
            stringResource(com.test.feature.R.string.lang_khmer),
            com.test.feature.R.drawable.ic_flag_kh
        ),
        LanguageModel(
            "th",
            stringResource(com.test.feature.R.string.lang_thai),
            com.test.feature.R.drawable.ic_flag_th
        ),
        LanguageModel(
            "uk",
            stringResource(com.test.feature.R.string.lang_ukrainian),
            com.test.feature.R.drawable.ic_flag_uk
        ),
        LanguageModel(
            "uz",
            stringResource(com.test.feature.R.string.lang_uzbek),
            com.test.feature.R.drawable.ic_flag_uz
        )
    )
    val jostFontFamily = FontFamily(
        Font(com.test.feature.R.font.jost_medium, FontWeight.W500, FontStyle.Normal),
        Font(com.test.feature.R.font.jost_semibold, FontWeight.W600, FontStyle.Normal)
    )
    val preSelectedLanguage by vm.preSelectedLanguageStateFlow.collectAsState()
    LaunchedEffect(1) {

    }

    BackHandler {
        onBack()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(UiColorTheme[isDarkMode].backgroundPrimary))
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
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
                text = stringResource(com.test.feature.R.string.language),
                fontSize = 22.sp,
                color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                fontWeight = FontWeight.W600,
                fontFamily = jostFontFamily
            )
            Spacer(Modifier.weight(1f))
            if (showOkMark) {
                Image(
                    modifier = modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable(
                            onClick = {
                                vm.preSelectedLanguageStateFlow.value?.let {

                                }
                                vm.selectLanguage()
                                onOkay()
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
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp, 12.dp, 16.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(langData) { (lang, text, img) ->
                LanguageCard(
                    text = text,
                    icon = img,
                    isDarkMode = isDarkMode,
                    selected = (preSelectedLanguage == lang),
                ) {
                    vm.preSelectLanguage(lang)
                }
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
