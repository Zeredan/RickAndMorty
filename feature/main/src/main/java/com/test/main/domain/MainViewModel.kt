package com.test.main.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.characters.model.Character
import com.test.local.repository.LocalCharactersRepository
import com.test.main.domain.filters.CharacterFilter
import com.test.remote.repository.RemoteCharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

@HiltViewModel
class MainViewModel(
    private val localCharactersRepository: LocalCharactersRepository,
    private val remoteCharactersRepository: RemoteCharactersRepository
) : ViewModel() {
    private val allSavedCharacters = localCharactersRepository.getCharactersAsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _filteredCharacters = MutableStateFlow(emptyList<Character>())
    val filteredCharacters = _filteredCharacters.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _characterSearchFilter = MutableStateFlow("")
    val characterSearchFilter = _characterSearchFilter.asStateFlow()

    private val _characterFilter = MutableStateFlow(
        CharacterFilter(
            status = "",
            species = "",
            type = "",
            gender = ""
        )
    )
    val characterFilter = _characterFilter.asStateFlow()

    fun updateCharacterSearchFilter(filter: String) {
        _characterSearchFilter.value = filter
    }

    fun updateCharacterFilter(filter: CharacterFilter) {
        _characterFilter.value = filter
    }

    fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            withTimeoutOrNull(5000) {
                localCharactersRepository.updateCharacters(remoteCharactersRepository.getCharacters())
            }
            delay(100)
            _isLoading.value = false
        }
    }

    fun filterCharacters() {
        _filteredCharacters.value = allSavedCharacters.value.filter { character ->
            (characterSearchFilter.value.isEmpty() || character.name.contains(_characterSearchFilter.value, ignoreCase = true)) &&
                    (characterFilter.value.status.isEmpty() || character.status == characterFilter.value.status) &&
                    (characterFilter.value.species.isEmpty() || character.species == characterFilter.value.species) &&
                    (characterFilter.value.type.isEmpty() || character.type == characterFilter.value.type) &&
                    (characterFilter.value.gender.isEmpty() || character.gender == characterFilter.value.gender)
        }
    }

    init {
        viewModelScope.launch {
            allSavedCharacters.collect {
                filterCharacters()
            }
        }
        viewModelScope.launch {
            characterSearchFilter.collect {
                filterCharacters()
            }
        }
        loadData()
    }
}