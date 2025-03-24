package com.leonardbauer.movieapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardbauer.movieapp.data.model.MovieDetails
import com.leonardbauer.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: MovieRepository) : ViewModel() {
    
    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()
    
    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            repository.getMovieDetails(movieId).fold(
                onSuccess = { movieDetails ->
                    _uiState.value = DetailsUiState.Success(movieDetails)
                },
                onFailure = { error ->
                    _uiState.value = DetailsUiState.Error(error.message ?: "Unknown error occurred")
                }
            )
        }
    }
}

sealed class DetailsUiState {
    data object Loading : DetailsUiState()
    data class Success(val movie: MovieDetails) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}