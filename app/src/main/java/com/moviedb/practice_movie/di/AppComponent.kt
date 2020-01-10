package com.moviedb.practice_movie.di

import com.moviedb.practice_movie.view.MainActivity
import com.moviedb.practice_movie.view.MovieDetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(movieDetailsActivity: MovieDetailsActivity)
}