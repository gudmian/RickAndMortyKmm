package com.example.rickandmortykmm.feature.location.impl

import com.example.rickandmortykmm.core.BaseViewModel
import com.example.rickandmortykmm.core.DispatcherProvider
import com.example.rickandmortykmm.core.subscribe
import com.example.rickandmortykmm.feature.location.api.LocationDetail
import com.example.rickandmortykmm.feature.location.api.LocationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationDetailViewModel(
    private val locationId: Int,
    private val repository: LocationRepository,
    private val dispatchers: DispatcherProvider,
) : BaseViewModel() {

    data class State(
        val isLoading: Boolean = true,
        val data: LocationDetail? = null,
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
            repository.getById(locationId)
                .onSuccess { _state.value = State(isLoading = false, data = it) }
                .onFailure { _state.value = State(isLoading = false, error = it.message ?: "Failed to load location") }
        }
    }

    fun observeState(onEach: (State) -> Unit): Job = state.subscribe(scope, onEach)
}
