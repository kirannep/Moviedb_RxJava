package com.moviedb.practice_movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviedb.practice_movie.common.Constants
import com.moviedb.practice_movie.data.movie_details.Movie_Details
import com.moviedb.practice_movie.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    val movieDetails: MutableLiveData<Movie_Details> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun getMovieDetails(movieId: Int) {
        compositeDisposable.add(
            repository.getMovieDetails(movieId, Constants.API_KEY).subscribe({ movieInfo ->
                movieDetails.value = movieInfo
            }, {
                error.value = it.localizedMessage
            })
        )
    }
}