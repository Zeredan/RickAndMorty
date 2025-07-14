package com.test.character.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.characters.model.Character
import com.test.local.repository.LocalCharactersRepository
import com.test.remote.repository.RemoteCharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val localCharactersRepository: LocalCharactersRepository,
    private val remoteCharactersRepository: RemoteCharactersRepository
) : ViewModel() {
    private val _character = MutableStateFlow<Character?>(null)
    val character = _character.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun loadData(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                withTimeoutOrNull(7000) {
                    withContext(Dispatchers.IO) {
                        localCharactersRepository.updateCharacter(remoteCharactersRepository.getCharacterInfo(id))
                    }
                }
                delay(100)
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }
    }

    fun initialize(id: Int) {
        viewModelScope.launch {
            localCharactersRepository.getCharacterByIdAsFlow(id).collect {
                _character.value = it
                _isLoading.value = false
            }
        }
    }
}