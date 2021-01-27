package com.zelyder.movie.data.mappers

import com.zelyder.movie.data.network.dto.CastDto
import com.zelyder.movie.data.network.dto.DetailsMovieDto
import com.zelyder.movie.data.network.dto.GenreDto
import com.zelyder.movie.data.network.dto.ListMovieDto
import com.zelyder.movie.domain.models.Actor
import com.zelyder.movie.domain.models.DetailsMovie
import com.zelyder.movie.domain.models.ListMovie


fun ListMovieDto.toListMovie(
    genresDto: List<GenreDto>,
    imagesBaseUrl: String
): ListMovie = ListMovie(
    id = this.id,
    title = this.title,
    poster = normalizedImgUrl(imagesBaseUrl, this.posterPicture),
    ratings = normalizedRating(this.rating),
    numberOfRatings = this.votesCount,
    minimumAge = normalizedMinimumAge(this.adult),
    releaseDate = this.releaseDate,
    genres = toGenresAsString(this, genresDto)
)

fun DetailsMovieDto.toDetailsMovie(
    actors: List<CastDto>,
    imagesBaseUrl: String
): DetailsMovie = DetailsMovie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    minimumAge = normalizedMinimumAge(this.adult),
    backdrop = normalizedImgUrl(imagesBaseUrl, this.backdropPicture),
    ratings = normalizedRating(this.rating),
    numberOfRatings = this.votesCount,
    runtime = this.duration,
    releaseDate = this.releaseDate,
    genres = toGenresAsString(this),
    actors = toActorsList(actors, this.id, imagesBaseUrl)
)

fun toActorsList(actorsDto: List<CastDto>, movieId: Int, imagesBaseUrl: String): List<Actor> =
    actorsDto.sortedBy { it.orderInCredits }
        .takeWhile { it.orderInCredits < ACTORS_LIMIT }
        .mapTo(mutableListOf()) {
            Actor(
                movieId = movieId,
                castId = it.castId,
                orderInCredits = it.orderInCredits,
                name = it.name,
                character = it.character,
                picture = normalizedImgUrl(imagesBaseUrl, it.profilePicture)
            )
        }


private fun normalizedRating(rating: Float?): Float {
    return (rating ?: 1f) / 2
}

private fun normalizedMinimumAge(isAdult: Boolean): String = if (isAdult) {
    AGE_ADULT
} else {
    AGE_CHILD
}

private fun toGenresAsString(movieDto: ListMovieDto, genresDto: List<GenreDto>) =
    genresDto.filter { movieDto.genreIds.contains(it.id) }
        .joinToString(", ") { it.name }

private fun toGenresAsString(movieDto: DetailsMovieDto) =
    movieDto.genres
        .joinToString(", ") { it.name }

private fun normalizedImgUrl(imagesBaseUrl: String, path: String?) =
    "${imagesBaseUrl}w342/${path}"


private const val AGE_ADULT = "18+"
private const val AGE_CHILD = "13+"
private const val ACTORS_LIMIT = 10