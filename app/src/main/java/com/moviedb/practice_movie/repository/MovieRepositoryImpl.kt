package com.moviedb.practice_movie.repository

import com.moviedb.practice_movie.data.movie_details.Movie_Details
import com.moviedb.practice_movie.data.movie_popular.Movie_Popular
import com.moviedb.practice_movie.network.WebServices
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val webServices: WebServices):MovieRepository {
    override fun getMovieDetails(movieId: Int, apiKey: String): Single<Movie_Details> =
        webServices.getMovieDetails(movieId,apiKey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun getMoviesPopular(apiKey: String): Single<Movie_Popular> =
        webServices.getPopularMovies(apiKey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

}