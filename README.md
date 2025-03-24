# Movie Explorer App

A modern Android app for exploring movies using the Open Movie Database (OMDb) API.

## Features

- **Home Screen**: Displays recommended movies
- **Search**: Search for movies by title
- **Details**: View detailed information about a selected movie
- **Navigation**: Easy navigation with bottom navigation bar

## Setup

To use this app, you need to get an API key from the [OMDb API website](https://www.omdbapi.com/apikey.aspx).

Once you have your API key, replace the placeholder in:
`app/src/main/java/com/leonardbauer/movieapp/data/api/OmdbApiService.kt`

```kotlin
// Replace with your actual OMDb API key
const val API_KEY = "YOUR_OMDB_API_KEY"
```

## Architecture

This app follows a MVVM (Model-View-ViewModel) architecture:

- **Model**: Data classes and repository for managing API requests
- **View**: Compose UI elements for displaying the content
- **ViewModel**: Manages UI state and business logic

## Libraries Used

- **Jetpack Compose**: Modern UI toolkit for Android
- **Retrofit**: Type-safe HTTP client for network requests
- **Gson**: JSON serialization/deserialization
- **Coil**: Image loading library for Compose
- **Navigation Compose**: Handle navigation between screens

## Requirements

- Android Studio Iguana or newer
- Kotlin 2.0+
- Android 7.0+ (API level 24)

## License

This project is open source and available under the [MIT License](LICENSE).