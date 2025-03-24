package com.leonardbauer.movieapp.data.repository

import com.leonardbauer.movieapp.data.api.OmdbApiService
import com.leonardbauer.movieapp.data.model.Movie
import com.leonardbauer.movieapp.data.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val apiService: OmdbApiService) {
    
    suspend fun searchMovies(query: String, page: Int = 1): List<Movie> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchMovies(query, page)
            if (response.response == "True") {
                response.movies
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getMovieDetails(imdbId: String): Result<MovieDetails> = withContext(Dispatchers.IO) {
        try {
            val movieDetails = apiService.getMovieDetails(imdbId)
            Result.success(movieDetails)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getRandomMovies(): List<Movie> = withContext(Dispatchers.IO) {
        // Get some popular movies using common search terms
        val searchTerms = listOf("star", "marvel", "love", "action", "2023")
        val randomTerm = searchTerms.random()
        
        try {
            val response = apiService.searchMovies(randomTerm)
            if (response.response == "True") {
                response.movies.shuffled().take(10)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}