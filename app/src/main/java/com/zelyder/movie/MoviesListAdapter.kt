package com.zelyder.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MoviesListAdapter(private val navigationClickListener: NavigationClickListener) :
    RecyclerView.Adapter<MoviesViewHolder>() {

    private var movies = listOf<Movie>()

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
            navigationClickListener.navigateToDetails(movie.id)
        }

    }

    override fun getItemCount(): Int = movies.size

    fun bindMovies(newMovies: List<Movie>) {
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
    private val tvDuration: TextView = itemView.findViewById(R.id.tvItemDuration)

    fun bind(movie: Movie) {
        tvTitle.text = movie.title
        ivCover.setImageResource(movie.smallCoverImg)
        tvAgeRating.text = movie.ageRating
        ivFavorite.setImageResource(
            when (movie.isFavorite) {
                true -> R.drawable.ic_like_filled
                false -> R.drawable.ic_like_empty
            }
        )
        tvGenres.text = movie.genres
        tvReviewsCount.text = itemView.context
            .getString(R.string.reviews_count_template, movie.reviewsCount)
        tvDuration.text = itemView.context
            .getString(R.string.duration_template, movie.duration)
        ratingBar.rating = movie.rating
    }

}