package com.moviedb.practice_movie.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.moviedb.practice_movie.viewmodel.MovieViewModelFactory
import com.moviedb.practice_movie.viewmodel.PopularMovieViewModel
import com.moviedb.practice_movie.R
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
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getSupportActionBar()?.hide();

        DaggerAppComponent.builder()
            .appModule(AppModule())
            .build()
            .inject(this)

        popularMovieViewModel = ViewModelProviders.of(this, movieViewModelFactory)
            .get(PopularMovieViewModel::class.java)
        popularMovieViewModel.getMoviePopular()
        popularMovieViewModel.movies.observe(this, Observer<List<Movies>> { movie ->
            movieAdapter(movie)
        })
        popularMovieViewModel.loadingState.observe(
            this,
            Observer<PopularMovieViewModel.LoadingState> {
                resetContent()
                when (it) {
                    PopularMovieViewModel.LoadingState.LOADING -> popular_movies_pg.visibility =
                        View.VISIBLE
                    PopularMovieViewModel.LoadingState.FAILURE -> errormsg_tv.visibility =
                        View.VISIBLE
                    PopularMovieViewModel.LoadingState.SUCCESS -> recyclerView.visibility =
                        View.VISIBLE
                }
            })
        popularMovieViewModel.error.observe(this, Observer<String> {
            errormsg_tv.text = it
            btn_retry.visibility = View.VISIBLE
        })

        btn_search.setOnClickListener {
            popularMovieViewModel.filterList(etQuery.text.toString().toLowerCase())
            Toast.makeText(this, "search clicked", Toast.LENGTH_SHORT).show()
        }

        btn_retry.setOnClickListener {
            popularMovieViewModel.getMoviePopular()
            btn_retry.visibility = View.GONE
        }

    }

    private fun movieAdapter(movie: List<Movies>) {
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

    private fun resetContent() {
        recyclerView.visibility = View.GONE
        popular_movies_pg.visibility = View.GONE
        errormsg_tv.visibility = View.GONE
    }

}
