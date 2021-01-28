package com.zelyder.movie.data.mappers

import com.zelyder.movie.data.network.dto.CastDto
import com.zelyder.movie.data.network.dto.DetailsMovieDto
import com.zelyder.movie.data.network.dto.GenreDto
import com.zelyder.movie.data.network.dto.ListMovieDto
import com.zelyder.movie.data.storage.entities.ActorEntity
import com.zelyder.movie.data.storage.entities.GenreEntity
import com.zelyder.movie.data.storage.entities.MovieEntity
import com.zelyder.movie.data.storage.entities.MovieWithActors
import com.zelyder.movie.domain.models.Actor
import com.zelyder.movie.domain.models.DetailsMovie
import com.zelyder.movie.domain.models.ListMovie


fun ActorEntity.toActor() = Actor(
    castId = this.castId,
    movieId = this.movieId,
    name = this.name,
    orderInCredits = this.orderInCredits,
    character = this.character,
    picture = this.picture
)

fun Actor.toActorEntity() = ActorEntity(
    castId = this.castId,
    movieId = this.movieId,
    name = this.name,
    orderInCredits = this.orderInCredits,
    character = this.character,
    picture = this.picture
)

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

fun MovieEntity.toListMovie(): ListMovie = ListMovie(
    id = this.id,
    title = this.title,
    poster = this.poster,
    ratings = this.ratings,
    numberOfRatings = this.numberOfRatings,
    minimumAge = this.minimumAge,
    releaseDate = this.releaseDate,
    genres = this.genres,
    isFavorite = this.isFavorite
)

fun ListMovie.toMovieEntity(): MovieEntity = MovieEntity(
    id = this.id,
    title = this.title,
    poster = this.poster,
    ratings = this.ratings,
    numberOfRatings = this.numberOfRatings,
    minimumAge = this.minimumAge,
    releaseDate = this.releaseDate,
    genres = this.genres,
    isFavorite = this.isFavorite
)

fun MovieWithActors.toDetailsMovie(): DetailsMovie = DetailsMovie(
    id = this.movie.id,
    title = this.movie.title,
    overview = this.movie.overview,
    minimumAge = this.movie.minimumAge,
    backdrop = this.movie.backdrop,
    ratings = this.movie.ratings,
    numberOfRatings = this.movie.numberOfRatings,
    runtime = this.movie.duration,
    releaseDate = this.movie.releaseDate,
    genres = this.movie.genres,
    actors = this.actors.map { it.toActor() },
    isFavorite = this.movie.isFavorite
)

fun DetailsMovie.toMovieEntity(): MovieWithActors = MovieWithActors(
    MovieEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        minimumAge = this.minimumAge,
        poster = this.poster,
        backdrop = this.backdrop,
        ratings = this.ratings,
        numberOfRatings = this.numberOfRatings,
        duration = this.runtime,
        releaseDate = this.releaseDate,
        genres = this.genres,
        isFavorite = this.isFavorite
    ),
    actors = this.actors.map { it.toActorEntity() },
)

fun DetailsMovieDto.toDetailsMovie(
    actors: List<Actor>,
    imagesBaseUrl: String,
    isFavorite: Boolean
): DetailsMovie = DetailsMovie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    minimumAge = normalizedMinimumAge(this.adult),
    poster = normalizedImgUrl(imagesBaseUrl,this.posterPicture),
    backdrop = normalizedImgUrl(imagesBaseUrl, this.backdropPicture),
    ratings = normalizedRating(this.rating),
    numberOfRatings = this.votesCount,
    runtime = this.duration,
    releaseDate = this.releaseDate,
    genres = toGenresAsString(this),
    actors = actors,
    isFavorite = isFavorite
)


fun GenreDto.toGenreEntity() = GenreEntity(
    id = this.id,
    name = this.name
)

fun GenreEntity.toGenreDto() = GenreDto(
    id = this.id,
    name = this.name
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