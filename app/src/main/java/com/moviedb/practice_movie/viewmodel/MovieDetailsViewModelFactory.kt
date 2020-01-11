package com.moviedb.practice_movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moviedb.practice_movie.repository.MovieRepository

class MovieDetailsViewModelFactory(private val repository: MovieRepository)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(repository) as T
    }
}