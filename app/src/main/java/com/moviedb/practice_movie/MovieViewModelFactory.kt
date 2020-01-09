package com.moviedb.practice_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviedb.practice_movie.repository.MovieRepository

class MovieViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PopularMovieViewModel(movieRepository) as T
    }

}