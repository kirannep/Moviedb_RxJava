package com.moviedb.practice_movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moviedb.practice_movie.common.Constants
import com.moviedb.practice_movie.data.movie_popular.Movie_Popular
import com.moviedb.practice_movie.data.movie_popular.Movies
import com.moviedb.practice_movie.repository.MovieRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class PopularMovieViewModelTest {

    @Rule
    @JvmField
    var rule:TestRule = InstantTaskExecutorRule()

    private val moviePopularMock: Observer<Movie_Popular> = mock()
    private val errormock:Observer<String> = mock()

    @Mock
    lateinit var repositorymock: MovieRepository
    private lateinit var popularMovieViewModel: PopularMovieViewModel
    val emptyListOfmovie = Movie_Popular(0,0,0, emptyList())
    val movie = Movie_Popular(1,5,2, mutableListOf<Movies>())
    val errormsg = "ErrorException"

    @Before
    fun setUp() {
        this.popularMovieViewModel = PopularMovieViewModel(repositorymock)
        popularMovieViewModel.moviepopular.observeForever(moviePopularMock)
        popularMovieViewModel.error.observeForever(errormock)

    }

    @Test
    fun getMovieReturnsEmptyList(){
        `when`(repositorymock.getMoviesPopular(Constants.API_KEY)).thenReturn(Single.just(emptyListOfmovie))
        popularMovieViewModel.getMoviePopular()
        verify(moviePopularMock).onChanged(emptyListOfmovie)
        verify(errormock, never()).onChanged(ArgumentMatchers.any())
    }

    @Test
    fun getMovieReturnsData(){
        `when`(repositorymock.getMoviesPopular(Constants.API_KEY)).thenReturn(Single.just(movie))
        popularMovieViewModel.getMoviePopular()
        verify(moviePopularMock).onChanged(movie)
        verify(errormock,never()).onChanged(ArgumentMatchers.anyString())
    }

    @Test
    fun getMovieReturnsError(){
        `when`(repositorymock.getMoviesPopular(Constants.API_KEY)).thenReturn(Single.error(RuntimeException(errormsg)))
        popularMovieViewModel.getMoviePopular()
        verify(errormock).onChanged(errormsg)
        verify(moviePopularMock,never()).onChanged(ArgumentMatchers.any())
    }
}