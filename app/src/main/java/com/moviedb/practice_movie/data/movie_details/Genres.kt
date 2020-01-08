package com.moviedb.practice_movie.data.movie_details

import com.google.gson.annotations.SerializedName


data class Genres (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String
)