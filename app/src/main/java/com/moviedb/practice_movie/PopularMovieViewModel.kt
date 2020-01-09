package com.moviedb.practice_movie

import androidx.lifecycle.ViewModel
import com.moviedb.practice_movie.repository.MovieRepository
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(private val movieRepository: MovieRepository):ViewModel() {

}