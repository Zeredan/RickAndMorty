package com.test.character.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import coil3.compose.AsyncImage
import com.test.character.R
import com.test.character.domain.CharacterViewModel
import com.test.feature.ui.UiColorTheme
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterFeatureRoot(
    modifier: Modifier = Modifier,
    id: Int,
    vm: CharacterViewModel = hiltViewModel(),
    onBack: () -> Unit,
    isDarkMode: Boolean
) {
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
    LaunchedEffect(1) {
        vm.initialize(id)
    }
    val context = LocalContext.current

    val character by vm.character.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val pullState = rememberPullToRefreshState(50.dp)
        LaunchedEffect(pullState.isRefreshing) {
            println(pullState)
            if (pullState.isRefreshing) {
                vm.loadData(id)
                vm.isLoading.first{ !it }
                pullState.endRefresh()
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(UiColorTheme[isDarkMode].backgroundPrimary))
                .nestedScroll(pullState.nestedScrollConnection)
                .verticalScroll(rememberScrollState())
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
                    text = character?.name ?: stringResource(com.test.feature.R.string.loading),
                    fontSize = 22.sp,
                    color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                    fontWeight = FontWeight.W600,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(100.dp),
                    strokeWidth = 6.dp,
                    strokeCap = StrokeCap.Round
                )
            } else {
                character?.let { ch ->
                    AsyncImage(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp)),
                        model = ch.image,
                        contentScale = ContentScale.Crop,
                        contentDescription = ch.name
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(com.test.feature.R.string.gender),
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W500,
                            fontFamily = jostFontFamily
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = ch.gender,
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W600,
                            fontFamily = jostFontFamily
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(com.test.feature.R.string.status),
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W500,
                            fontFamily = jostFontFamily
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            ch.status,
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W600,
                            fontFamily = jostFontFamily
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(com.test.feature.R.string.species),
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W500,
                            fontFamily = jostFontFamily
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            ch.species,
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W600,
                            fontFamily = jostFontFamily
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(com.test.feature.R.string.type),
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W500,
                            fontFamily = jostFontFamily
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            ch.type.takeIf { it.isNotEmpty() } ?: "???",
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W600,
                            fontFamily = jostFontFamily
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(com.test.feature.R.string.origin),
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W500,
                            fontFamily = jostFontFamily
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            ch.origin.name,
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W600,
                            fontFamily = jostFontFamily
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(com.test.feature.R.string.location),
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W500,
                            fontFamily = jostFontFamily
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            ch.location.name,
                            fontSize = 18.sp,
                            color = colorResource(UiColorTheme[isDarkMode].textPrimary),
                            fontWeight = FontWeight.W600,
                            fontFamily = jostFontFamily
                        )
                    }
                }
            }
        }
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullState,
        )
    }
}