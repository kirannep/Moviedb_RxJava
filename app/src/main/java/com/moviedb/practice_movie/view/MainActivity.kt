package com.moviedb.practice_movie.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.moviedb.practice_movie.MovieViewModelFactory
import com.moviedb.practice_movie.PopularMovieViewModel
import com.moviedb.practice_movie.R
import com.moviedb.practice_movie.data.movie_popular.Movie_Popular
import com.moviedb.practice_movie.data.movie_popular.Results
import com.moviedb.practice_movie.di.AppModule
import com.moviedb.practice_movie.di.DaggerAppComponent
import kotlinx.android.synthetic.main.activity_main.*
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

        popularMovieViewModel = ViewModelProviders.of(this, movieViewModelFactory)
            .get(PopularMovieViewModel::class.java)
        popularMovieViewModel.getMoviePopular()
        popularMovieViewModel.moviepopular.observe(this, Observer<Movie_Popular> { movie ->
            Log.d("movieTitle", movie.results[1].original_title)
            movieAdapter(movie)
        })
    }

    private fun movieAdapter(movie: Movie_Popular) {
        val adapter = MovieAdapter(movie, object : OnMovieClickListener {
            override fun onMovieClicked(results: Results) {
                val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("movieId", results.id)
                startActivity(intent)
            }
        })
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }
}
