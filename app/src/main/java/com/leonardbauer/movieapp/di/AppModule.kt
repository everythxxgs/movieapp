package com.leonardbauer.movieapp.di

import com.leonardbauer.movieapp.data.api.OmdbApiService
import com.leonardbauer.movieapp.data.repository.MovieRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppModule {
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    
    fun provideApiService(client: OkHttpClient): OmdbApiService {
        return Retrofit.Builder()
            .baseUrl(OmdbApiService.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmdbApiService::class.java)
    }
    
    fun provideMovieRepository(apiService: OmdbApiService): MovieRepository {
        return MovieRepository(apiService)
    }
}