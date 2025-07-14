package com.test.splash.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
): ViewModel() {
    private var isInitialized = false
    lateinit var onSuccess: () -> Unit
    lateinit var onError: () -> Unit

    var progress by mutableFloatStateOf(0f)

    fun initialize(
        jobsToWait: List<Job>,
        deferredsToWait: List<Deferred<Any>>,
    ) {
        if (!isInitialized || true) {
            viewModelScope.launch {
                try {
                    isInitialized = true

                    var completedTasks = 0
                    val totalTasks = deferredsToWait.size

                    jobsToWait.forEach { it.join() }

                    if (totalTasks > 0) {
                        deferredsToWait.forEach { deferred ->
                            deferred.await()
                            completedTasks++

                            val targetProgress = completedTasks.toFloat() / totalTasks

                            val startProgress = progress
                            val progressDiff = targetProgress - startProgress

                            val steps = 30
                            for (step in 1..steps) {
                                val fraction = step.toFloat() / steps
                                progress = startProgress + (progressDiff * fraction)
                                delay(10)
                            }

                            progress = targetProgress
                        }
                    } else {
                        val startProgress = progress
                        val progressDiff = 1f - startProgress

                        val steps = 30
                        for (step in 1..steps) {
                            val fraction = step.toFloat() / steps
                            progress = startProgress + (progressDiff * fraction)
                            delay(10)
                        }

                        progress = 1f
                    }
                    onSuccess()
                }
                catch (e: Exception) {
                    onError()
                }
            }
        }
    }
}