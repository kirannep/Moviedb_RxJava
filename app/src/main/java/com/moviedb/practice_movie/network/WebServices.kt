package com.moviedb.practice_movie.network

import com.moviedb.practice_movie.data.movie_details.Movie_Details
import com.moviedb.practice_movie.data.movie_popular.Movie_Popular
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServices {

    @GET("popular")
    fun getPopularMovies(@Query("api_key") apikey:String) : Single<Movie_Popular>

    @GET("{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId:Int,@Query("api_key") apikey: String) : Single<Movie_Details>

}