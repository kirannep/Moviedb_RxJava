package com.moviedb.practice_movie.data.movie_popular

import com.google.gson.annotations.SerializedName

data class Movie_Popular (

	@SerializedName("page") val page : Int,
	@SerializedName("total_results") val total_results : Int,
	@SerializedName("total_pages") val total_pages : Int,
	@SerializedName("results") val results : List<Results>
)