package com.test.splash.ui

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
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
import com.test.splash.domain.SplashViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashFeatureRoot(
    modifier: Modifier = Modifier,
    vm: SplashViewModel = hiltViewModel(),
    jobsToWait: List<Job>,
    deferredsToWait: List<Deferred<Any>>,
    onSuccess: () -> Unit,
    onError: () -> Unit
) {
    val jostFontFamily = FontFamily(
        Font(com.test.feature.R.font.jost_medium, FontWeight.W500, FontStyle.Normal),
        Font(com.test.feature.R.font.jost_semibold, FontWeight.W600, FontStyle.Normal)
    )
    val activity = LocalContext.current as ComponentActivity
    val window = activity.window
    DisposableEffect(1) {
        window.navigationBarColor = activity.resources.getColor(com.test.feature.R.color.white)
        window.statusBarColor = activity.resources.getColor(com.test.feature.R.color.white)

        onDispose {
            window.navigationBarColor = activity.resources.getColor(com.test.feature.R.color.bg_primary)
            window.statusBarColor = activity.resources.getColor(com.test.feature.R.color.bg_primary)
        }
    }

    LaunchedEffect(1) {
        vm.onSuccess = onSuccess
        vm.onError = onError
        vm.initialize(
            jobsToWait, deferredsToWait
        )
    }

    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(com.test.feature.R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth(),
            painter = painterResource(com.test.feature.R.drawable.logo),
            contentDescription = null
        )
        Spacer(Modifier.height(17.dp))
        Text(
            text = stringResource(com.test.feature.R.string.rick_and_morty),
            fontSize = 22.sp,
            color = colorResource(com.test.feature.R.color.dark_blue),
            fontWeight = FontWeight.W600,
            fontFamily = jostFontFamily
        )
        Spacer(Modifier.height(29.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .height(8.dp),
            progress = {
                vm.progress
            },
            trackColor = colorResource(com.test.feature.R.color.bg_secondary),
            color = colorResource(com.test.feature.R.color.main_blue),
            strokeCap = StrokeCap.Round
        )
        Spacer(Modifier.height(102.dp))
        Spacer(Modifier.weight(1f))
    }
}