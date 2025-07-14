package com.test.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.test.feature.ui.RunningText
import com.test.main.domain.MainViewModel
import com.test.main.domain.filters.Status
import kotlinx.coroutines.flow.first
import kotlin.math.ceil
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersFeatureRoot(
    modifier: Modifier = Modifier,
    vm: MainViewModel,
    onFilterClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
    onCharacterClicked: (Int) -> Unit
) {
    val jostFontFamily = FontFamily(
        Font(com.test.feature.R.font.jost_medium, FontWeight.W500),
        Font(com.test.feature.R.font.jost_semibold, FontWeight.W600)
    )

    val isLoading by vm.isLoading.collectAsState()
    val characterSearchFilter by vm.characterSearchFilter.collectAsState()
    val filteredCharacters by vm.filteredCharacters.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val pullState = rememberPullToRefreshState(50.dp)
        LaunchedEffect(pullState.isRefreshing) {
            println(pullState)
            if (pullState.isRefreshing) {
                vm.loadData()
                vm.isLoading.first{ !it }
                pullState.endRefresh()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 11.dp, 0.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(com.test.feature.R.string.rick_and_morty),
                    color = colorResource(com.test.feature.R.color.dark_blue),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = jostFontFamily
                )
                Spacer(Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onSettingsClicked()
                        }
                        .padding(5.dp),
                    painter = painterResource(com.test.feature.R.drawable.settings),
                    contentDescription = null
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                value = characterSearchFilter,
                onValueChange = {
                    vm.updateCharacterSearchFilter(it)
                },
                label = {
                    Text(
                        text = stringResource(com.test.feature.R.string.search_characters),
                        color = colorResource(com.test.feature.R.color.dark_blue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = jostFontFamily
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(com.test.feature.R.color.bg_primary),
                    focusedContainerColor = colorResource(com.test.feature.R.color.bg_primary),
                    unfocusedTextColor = colorResource(com.test.feature.R.color.dark_blue),
                    focusedTextColor = colorResource(com.test.feature.R.color.dark_blue)
                )
            )
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(100.dp),
                    strokeWidth = 6.dp,
                    strokeCap = StrokeCap.Round
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 16.dp, 0.dp)
                        .weight(1f)
                        .fillMaxWidth()
                        .nestedScroll(pullState.nestedScrollConnection)
                ) {
                    val rowsCount = ceil(filteredCharacters.size / 2.0).roundToInt()
                    items(rowsCount) { rowIndex ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(2) { j ->
                                if (rowIndex * 2 + j < filteredCharacters.size) {
                                    val character = filteredCharacters[rowIndex * 2 + j]
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(0.8f)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(colorResource(com.test.feature.R.color.gray))
                                            .clickable {
                                                onCharacterClicked(character.id)
                                            },
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .weight(4f) //+2f
                                                .fillMaxWidth(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            AsyncImage(
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                                model = character.image,
                                                contentScale = ContentScale.Crop,
                                                contentDescription = character.name
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .align(Alignment.BottomEnd)
                                                    .clip(
                                                        RoundedCornerShape(
                                                            10.dp,
                                                            0.dp,
                                                            0.dp,
                                                            0.dp
                                                        )
                                                    )
                                                    .background(colorResource(com.test.feature.R.color.black1))
                                                    .padding(4.dp),
                                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .size(7.dp)
                                                        .background(
                                                            colorResource(
                                                                when (character.status) {
                                                                    Status.ALIVE -> com.test.feature.R.color.green
                                                                    Status.DEAD -> com.test.feature.R.color.red
                                                                    else -> com.test.feature.R.color.purple
                                                                }
                                                            )
                                                        )
                                                )
                                                Text(
                                                    text = stringResource(
                                                        when (character.status) {
                                                            Status.ALIVE -> com.test.feature.R.string.alive
                                                            Status.DEAD -> com.test.feature.R.string.dead
                                                            else -> com.test.feature.R.string.unknown
                                                        }
                                                    ),
                                                    color = colorResource(com.test.feature.R.color.main_blue),
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.W600,
                                                    fontFamily = jostFontFamily
                                                )
                                            }
                                        }
                                        Column(
                                            modifier = Modifier
                                                .weight(2f)
                                                .fillMaxWidth()
                                                .background(colorResource(com.test.feature.R.color.black))
                                                .padding(16.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            RunningText(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                text = character.name,
                                                textColor = colorResource(com.test.feature.R.color.main_blue)
                                            )
                                            Text(
                                                text = "${character.gender} | ${character.species}",
                                                color = colorResource(com.test.feature.R.color.purple),
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.W500,
                                                fontFamily = jostFontFamily
                                            )
                                        }
                                    }
                                } else {
                                    Spacer(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                    item {
                        Spacer(Modifier.height(36.dp))
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(36.dp)
                .clip(CircleShape)
                .size(80.dp)
                .background(colorResource(com.test.feature.R.color.purple))
                .clickable {
                    onFilterClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(com.test.feature.R.drawable.filter),
                contentDescription = null
            )
        }
        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullState,
        )
    }
}