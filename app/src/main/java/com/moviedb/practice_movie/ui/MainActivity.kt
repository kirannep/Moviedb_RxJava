package com.moviedb.practice_movie.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.moviedb.practice_movie.viewmodel.MovieViewModelFactory
import com.moviedb.practice_movie.viewmodel.PopularMovieViewModel
import com.moviedb.practice_movie.R
import com.moviedb.practice_movie.data.movie_popular.Movie_Popular
import com.moviedb.practice_movie.data.movie_popular.Movies
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
            hideProgressBar()
            movieAdapter(movie)
        })
        popularMovieViewModel.loadingState.observe(this,Observer<PopularMovieViewModel.LoadingState>{
            when(it){ is PopularMovieViewModel.LoadingState.LOADING -> displayProgressBar()}
        })
    }

    private fun movieAdapter(movie: Movie_Popular) {
        val adapter = MovieAdapter(movie, object : OnMovieClickListener {
            override fun onMovieClicked(movies: Movies) {
                val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("movieId", movies.id)
                startActivity(intent)
            }
        })
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    private fun displayProgressBar(){
        popular_movies_pg.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun hideProgressBar(){
        popular_movies_pg.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}
