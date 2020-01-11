package com.moviedb.practice_movie.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moviedb.practice_movie.R
import com.moviedb.practice_movie.common.Constants
import com.moviedb.practice_movie.data.movie_details.Movie_Details
import com.moviedb.practice_movie.di.AppModule
import com.moviedb.practice_movie.di.DaggerAppComponent
import com.moviedb.practice_movie.viewmodel.MovieDetailsViewModel
import com.moviedb.practice_movie.viewmodel.MovieDetailsViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var movieDetailsViewModelFactory: MovieDetailsViewModelFactory
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        DaggerAppComponent.builder()
            .appModule(AppModule())
            .build()
            .inject(this)

        movieDetailsViewModel = ViewModelProviders.of(this, movieDetailsViewModelFactory)
            .get(MovieDetailsViewModel::class.java)
        val movieId = intent.getIntExtra("movieId", 0)
        movieDetailsViewModel.getMovieDetails(movieId)
        movieDetailsViewModel.movieDetails.observe(this, Observer<Movie_Details> { movie ->
            hideProgressBar()
            title_details_tv.text = movie.original_title
            Picasso.get().load(Constants.PICTURE_PATH + movie.poster_path).into(movie_details_iv)
            overview_tv.text = movie.overview
        })

        movieDetailsViewModel.loadingState.observe(
            this,
            Observer<MovieDetailsViewModel.LoadingState> {
                when (it) {
                    is MovieDetailsViewModel.LoadingState.LOADING -> displayProgressBar()
                }
            })
    }

    private fun displayProgressBar() {
        movie_details_pg.visibility = View.VISIBLE
        movie_details_iv.visibility = View.GONE
        overview_tv.visibility = View.GONE
        title_details_tv.visibility = View.GONE
    }

    private fun hideProgressBar() {
        movie_details_pg.visibility = View.GONE
        movie_details_iv.visibility = View.VISIBLE
        overview_tv.visibility = View.VISIBLE
        title_details_tv.visibility = View.VISIBLE
    }
}
