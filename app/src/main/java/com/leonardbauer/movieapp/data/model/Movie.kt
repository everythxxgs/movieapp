package com.leonardbauer.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("imdbID")
    val id: String,
    
    @SerializedName("Title")
    val title: String,
    
    @SerializedName("Year")
    val year: String,
    
    @SerializedName("Poster")
    val posterUrl: String,
    
    @SerializedName("Type")
    val type: String
)

data class MovieDetails(
    @SerializedName("imdbID")
    val id: String,
    
    @SerializedName("Title")
    val title: String,
    
    @SerializedName("Year")
    val year: String,
    
    @SerializedName("Rated")
    val rated: String,
    
    @SerializedName("Released")
    val released: String,
    
    @SerializedName("Runtime")
    val runtime: String,
    
    @SerializedName("Genre")
    val genre: String,
    
    @SerializedName("Director")
    val director: String,
    
    @SerializedName("Writer")
    val writer: String,
    
    @SerializedName("Actors")
    val actors: String,
    
    @SerializedName("Plot")
    val plot: String,
    
    @SerializedName("Language")
    val language: String,
    
    @SerializedName("Country")
    val country: String,
    
    @SerializedName("Awards")
    val awards: String,
    
    @SerializedName("Poster")
    val posterUrl: String,
    
    @SerializedName("Ratings")
    val ratings: List<Rating>,
    
    @SerializedName("Metascore")
    val metascore: String,
    
    @SerializedName("imdbRating")
    val imdbRating: String,
    
    @SerializedName("imdbVotes")
    val imdbVotes: String,
    
    @SerializedName("Type")
    val type: String
)

data class Rating(
    @SerializedName("Source")
    val source: String,
    
    @SerializedName("Value")
    val value: String
)

data class SearchResponse(
    @SerializedName("Search")
    val movies: List<Movie>,
    
    @SerializedName("totalResults")
    val totalResults: String,
    
    @SerializedName("Response")
    val response: String
)