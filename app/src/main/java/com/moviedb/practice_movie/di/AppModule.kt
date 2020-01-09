package com.moviedb.practice_movie.di

import com.moviedb.practice_movie.MovieViewModelFactory
import com.moviedb.practice_movie.common.Constants
import com.moviedb.practice_movie.network.WebServices
import com.moviedb.practice_movie.repository.MovieRepository
import com.moviedb.practice_movie.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideWebServices(retrofit: Retrofit): WebServices {
        return retrofit.create(WebServices::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpCient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideMovieRepository(webServices: WebServices): MovieRepository {
        return MovieRepositoryImpl(webServices)
    }

    @Provides
    @Singleton
    fun provideMovieViewModelFactory(movieRepository: MovieRepository): MovieViewModelFactory {
        return MovieViewModelFactory(movieRepository)
    }

}