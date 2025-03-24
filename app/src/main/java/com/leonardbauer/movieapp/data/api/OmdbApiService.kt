package com.leonardbauer.movieapp.data.api

import com.leonardbauer.movieapp.data.model.MovieDetails
import com.leonardbauer.movieapp.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") searchQuery: String,
        @Query("page") page: Int = 1,
        @Query("apikey") apiKey: String = API_KEY
    ): SearchResponse
    
    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("plot") plot: String = "full",
        @Query("apikey") apiKey: String = API_KEY
    ): MovieDetails
    
    companion object {
        const val BASE_URL = "https://www.omdbapi.com/"
        
        const val API_KEY = "ab6365b"
    }
}