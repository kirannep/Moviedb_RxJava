package com.moviedb.practice_movie.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.practice_movie.R
import com.moviedb.practice_movie.common.Constants
import com.moviedb.practice_movie.data.movie_popular.Movies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_card_view.view.*

class MovieAdapter(private val movies: List<Movies>, private val listener:OnMovieClickListener)
    :RecyclerView.Adapter<MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_card_view,parent,false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.tvTitle.text = movies[position].original_title
        Picasso.get().load(Constants.PICTURE_PATH + movies[position].poster_path).into(holder.img)
        holder.bind(movies[position],listener)
    }

}

class MovieViewHolder(view: View):RecyclerView.ViewHolder(view){
    fun bind(movies: Movies, listener: OnMovieClickListener){
        itemView.setOnClickListener {
            listener.onMovieClicked(movies)
        }
    }
    val tvTitle = view.movie_title_tv
    val img = view.movie_iv
}

interface OnMovieClickListener{
    fun onMovieClicked(movies: Movies)
}