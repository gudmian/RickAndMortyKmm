package com.example.rickandmortykmm.feature.character.impl

import com.example.rickandmortykmm.core.BaseViewModel
import com.example.rickandmortykmm.core.DispatcherProvider
import com.example.rickandmortykmm.core.subscribe
import com.example.rickandmortykmm.feature.character.api.CharacterDetail
import com.example.rickandmortykmm.feature.character.api.CharacterRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val characterId: Int,
    private val repository: CharacterRepository,
    private val dispatchers: DispatcherProvider,
) : BaseViewModel() {

    data class State(
        val isLoading: Boolean = true,
        val data: CharacterDetail? = null,
        val error: String? = null,
    )

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        _state.value = State(isLoading = true)
        scope.launch(dispatchers.io) {
            repository.getById(characterId)
                .onSuccess { _state.value = State(isLoading = false, data = it) }
                .onFailure { _state.value = State(isLoading = false, error = it.message ?: "Failed to load character") }
        }
    }

    /** For iOS/UIKit: non-suspending subscription returning a cancellable [Job]. */
    fun observeState(onEach: (State) -> Unit): Job = state.subscribe(scope, onEach)
}
