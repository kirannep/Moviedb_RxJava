package com.moviedb.practice_movie.repository

import com.moviedb.practice_movie.data.movie_details.Movie_Details
import com.moviedb.practice_movie.data.movie_popular.Movie_Popular
import io.reactivex.Single

interface MovieRepository {
    fun getMoviesPopular(apiKey:String): Single<Movie_Popular>
    fun getMovieDetails(movieId:Int,apiKey: String): Single<Movie_Details>
}