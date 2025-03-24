package com.leonardbauer.movieapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardbauer.movieapp.data.model.Movie
import com.leonardbauer.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class SearchViewModel(private val repository: MovieRepository) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    
    private var searchJob: Job? = null
    private val searchDebounceTime = 500L // 500ms debounce time
    
    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        
        // Cancel previous search if it's still running
        searchJob?.cancel()
        
        if (query.trim().isBlank()) {
            // Clear results if query is empty
            _uiState.update { it.copy(movies = emptyList(), error = null, isLoading = false) }
            return
        }
        
        // Debounce search to avoid API spam while typing
        searchJob = viewModelScope.launch {
            delay(searchDebounceTime)
            searchMovies(query)
        }
    }
    
    private fun searchMovies(query: String = _uiState.value.searchQuery) {
        val trimmedQuery = query.trim()
        if (trimmedQuery.isBlank()) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val movies = repository.searchMovies(trimmedQuery)
                _uiState.update { 
                    it.copy(
                        movies = movies, 
                        isLoading = false,
                        error = if (movies.isEmpty()) "No movies found for '$trimmedQuery'" else null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = e.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }
    
    // For explicit search button presses
    fun searchMovies() {
        searchJob?.cancel()
        searchMovies(_uiState.value.searchQuery)
    }
}

data class SearchUiState(
    val searchQuery: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)