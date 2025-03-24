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

class SearchViewModel(private val repository: MovieRepository) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    
    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
    
    fun searchMovies() {
        val query = _uiState.value.searchQuery.trim()
        if (query.isBlank()) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val movies = repository.searchMovies(query)
                _uiState.update { 
                    it.copy(
                        movies = movies, 
                        isLoading = false,
                        error = if (movies.isEmpty()) "No movies found for '$query'" else null
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
}

data class SearchUiState(
    val searchQuery: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)