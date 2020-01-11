package com.moviedb.practice_movie.di

import com.moviedb.practice_movie.ui.MainActivity
import com.moviedb.practice_movie.ui.MovieDetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(movieDetailsActivity: MovieDetailsActivity)
}