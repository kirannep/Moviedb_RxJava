package com.moviedb.practice_movie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moviedb.practice_movie.MovieViewModelFactory
import com.moviedb.practice_movie.PopularMovieViewModel
import com.moviedb.practice_movie.R
import com.moviedb.practice_movie.data.movie_popular.Movie_Popular
import com.moviedb.practice_movie.di.AppModule
import com.moviedb.practice_movie.di.DaggerAppComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory
    private lateinit var popularMovieViewModel: PopularMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerAppComponent.builder()
            .appModule(AppModule())
            .build()
            .inject(this)

        popularMovieViewModel = ViewModelProviders.of(this,movieViewModelFactory).get(PopularMovieViewModel::class.java)
        popularMovieViewModel.moviepopular.observe(this,Observer<Movie_Popular>{movie ->

        })

        popularMovieViewModel.getMoviePopular()
    }
}
