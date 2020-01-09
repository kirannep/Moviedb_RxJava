package com.moviedb.practice_movie.di

import com.moviedb.practice_movie.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
 fun inject(mainActivity: MainActivity)
}