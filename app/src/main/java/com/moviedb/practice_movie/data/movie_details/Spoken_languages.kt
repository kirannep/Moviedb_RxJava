package com.moviedb.practice_movie.data.movie_details

import com.google.gson.annotations.SerializedName

data class Spoken_languages(

    @SerializedName("iso_639_1") val iso_639_1: String,
    @SerializedName("name") val name: String
)