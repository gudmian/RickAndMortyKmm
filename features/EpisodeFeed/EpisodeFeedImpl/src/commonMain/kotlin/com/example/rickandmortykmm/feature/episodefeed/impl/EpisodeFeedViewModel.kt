package com.example.rickandmortykmm.feature.episodefeed.impl

import com.example.rickandmortykmm.core.BaseViewModel
import com.example.rickandmortykmm.core.DispatcherProvider
import com.example.rickandmortykmm.core.subscribe
import com.example.rickandmortykmm.feature.episodefeed.api.EpisodeFeedItem
import com.example.rickandmortykmm.feature.episodefeed.api.EpisodeFeedRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EpisodeFeedViewModel(
    private val repository: EpisodeFeedRepository,
    private val dispatchers: DispatcherProvider,
) : BaseViewModel() {

    data class State(
        val items: List<EpisodeFeedItem> = emptyList(),
        val isLoading: Boolean = false,
        val isLoadingMore: Boolean = false,
        val error: String? = null,
        val loadMoreError: String? = null,
        val endReached: Boolean = false,
    )

    private val _state = MutableStateFlow(State(isLoading = true))
    val state: StateFlow<State> = _state.asStateFlow()

    private var currentPage = 0

    init {
        load()
    }

    fun load() {
        currentPage = 0
        _state.value = State(isLoading = true)
        scope.launch(dispatchers.io) {
            repository.getPage(1)
                .onSuccess { page ->
                    currentPage = 1
                    _state.value = State(items = page.items, endReached = !page.hasNext)
                }
                .onFailure { e ->
                    _state.value = State(error = e.message ?: "Failed to load episodes")
                }
        }
    }

    fun loadMore() {
        val current = _state.value
        if (current.isLoading || current.isLoadingMore || current.endReached) return
        if (current.error != null) return
        _state.value = current.copy(isLoadingMore = true, loadMoreError = null)
        scope.launch(dispatchers.io) {
            repository.getPage(currentPage + 1)
                .onSuccess { page ->
                    currentPage += 1
                    _state.value = _state.value.copy(
                        items = _state.value.items + page.items,
                        isLoadingMore = false,
                        endReached = !page.hasNext,
                    )
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(
                        isLoadingMore = false,
                        loadMoreError = e.message ?: "Failed to load more",
                    )
                }
        }
    }

    fun retry() = load()

    fun retryLoadMore() = loadMore()

    fun observeState(onEach: (State) -> Unit): Job = state.subscribe(scope, onEach)
}
