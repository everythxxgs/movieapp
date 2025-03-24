package com.leonardbauer.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardbauer.movieapp.data.model.Movie
import com.leonardbauer.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadRecommendedMovies()
    }
    
    fun loadRecommendedMovies() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val movies = repository.getRandomMovies()
                _uiState.value = if (movies.isNotEmpty()) {
                    HomeUiState.Success(movies)
                } else {
                    HomeUiState.Error("No movies found")
                }
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val movies: List<Movie>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}