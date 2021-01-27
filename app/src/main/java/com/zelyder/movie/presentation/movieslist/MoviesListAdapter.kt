package com.zelyder.movie.presentation.movieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zelyder.movie.presentation.core.NavigationClickListener
import com.zelyder.movie.R
import com.zelyder.movie.domain.models.DetailsMovie
import com.zelyder.movie.domain.models.ListMovie

class MoviesListAdapter(private val navigationClickListener: NavigationClickListener?) :
    RecyclerView.Adapter<MoviesViewHolder>() {

    private var movies = listOf<ListMovie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            navigationClickListener?.navigateToDetails(movie.id)
        }

    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<ListMovie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

}

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle: TextView = itemView.findViewById(R.id.tvItemTitle)
    private val ivCover: ImageView = itemView.findViewById(R.id.imgItemPoster)
    private val tvAgeRating: TextView = itemView.findViewById(R.id.tvItemAgeRating)
    private val ivFavorite: ImageView = itemView.findViewById(R.id.ivItemFavorite)
    private val tvGenres: TextView = itemView.findViewById(R.id.tvItemGenres)
    private val ratingBar: RatingBar = itemView.findViewById(R.id.itemRatingBar)
    private val tvReviewsCount: TextView = itemView.findViewById(R.id.tvItemReviewsCount)
    private val tvReleaseDate: TextView = itemView.findViewById(R.id.tvItemReleaseDate)

    fun bind(movie: ListMovie) {
        tvTitle.text = movie.title
        Picasso.get().load(movie.poster)
            .into(ivCover)
        tvAgeRating.text = movie.minimumAge
        ivFavorite.setImageResource(
            when (movie.isFavorite) {
                true -> R.drawable.ic_like_filled
                false -> R.drawable.ic_like_empty
            }
        )
        tvGenres.text = movie.genres
        tvReviewsCount.text = itemView.context
            .getString(R.string.reviews_count_template, movie.numberOfRatings)
        tvReleaseDate.text = movie.releaseDate
        ratingBar.rating = movie.ratings

        ivFavorite.setOnClickListener {
            ivFavorite.setImageResource(
                when (movie.isFavorite) {
                    true -> {
                        movie.isFavorite = false
                        R.drawable.ic_like_empty
                    }
                    false -> {
                        movie.isFavorite = true
                        R.drawable.ic_like_filled
                    }
                }
            )
        }
    }

}