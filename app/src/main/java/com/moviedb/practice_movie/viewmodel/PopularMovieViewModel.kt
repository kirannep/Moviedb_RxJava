package com.moviedb.practice_movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviedb.practice_movie.common.Constants
import com.moviedb.practice_movie.data.movie_popular.Movie_Popular
import com.moviedb.practice_movie.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    val moviepopular: MutableLiveData<Movie_Popular> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()
    val error: MutableLiveData<String> = MutableLiveData()
    val loadingState = MutableLiveData<LoadingState>()

    fun getMoviePopular() {
        loadingState.value = LoadingState.LOADING
        compositeDisposable.add(
            movieRepository.getMoviesPopular(Constants.API_KEY)
                .subscribe({ movies ->
                    moviepopular.value = movies
                }, {
                    error.value = it.localizedMessage
                }
                )
        )
    }


    sealed class LoadingState {
        object LOADING : LoadingState()
    }
}