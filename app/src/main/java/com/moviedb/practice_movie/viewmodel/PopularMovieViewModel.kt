package com.moviedb.practice_movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviedb.practice_movie.common.Constants
import com.moviedb.practice_movie.data.movie_popular.Movies
import com.moviedb.practice_movie.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PopularMovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    val movies: MutableLiveData<List<Movies>> = MutableLiveData()
    val fullmovielist = mutableListOf<Movies>()
    private val compositeDisposable = CompositeDisposable()
    val error: MutableLiveData<String> = MutableLiveData()
    val loadingState = MutableLiveData<LoadingState>()

    fun getMoviePopular() {
        loadingState.value = LoadingState.LOADING
        compositeDisposable.add(
            movieRepository.getMoviesPopular(Constants.API_KEY)
                .subscribe({ movieresponse ->
                    movies.value = movieresponse.results
                    fullmovielist.clear()
                    fullmovielist.addAll(movieresponse.results)
                    loadingState.value = LoadingState.SUCCESS
                }, {
                    it.printStackTrace()
                    error.value = it.localizedMessage
                    loadingState.value = LoadingState.FAILURE
                })
        )
    }

    sealed class LoadingState {
        object LOADING : LoadingState()
        object SUCCESS : LoadingState()
        object FAILURE : LoadingState()
    }

    fun filterList(keyword: String) {
        movies.value = fullmovielist.filter {  it.title.toLowerCase().contains(keyword) }
    }
}