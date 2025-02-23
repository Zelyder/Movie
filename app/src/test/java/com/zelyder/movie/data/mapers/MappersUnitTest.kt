package com.zelyder.movie.data.mappers

import com.zelyder.movie.data.network.dto.CastDto
import com.zelyder.movie.data.network.dto.GenreDto
import com.zelyder.movie.data.storage.entities.ActorEntity
import com.zelyder.movie.data.storage.entities.GenreEntity
import com.zelyder.movie.domain.models.Actor
import com.zelyder.movie.domain.models.ListMovie
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersUnitTest {

    @Test
    fun `ActorEntity to Actor conversion should be correct`() {
        val actorEntity = ActorEntity(1, 2, "Name", 3, "Character", "Picture")
        val actor = actorEntity.toActor()
        assertEquals(actorEntity.castId, actor.castId)
        assertEquals(actorEntity.movieId, actor.movieId)
        assertEquals(actorEntity.name, actor.name)
        assertEquals(actorEntity.orderInCredits, actor.orderInCredits)
        assertEquals(actorEntity.character, actor.character)
        assertEquals(actorEntity.picture, actor.picture)
    }

    @Test
    fun `Actor to ActorEntity conversion should be correct`() {
        val actor = Actor(1, 2, "Name", 3, "Character", "Picture")
        val actorEntity = actor.toActorEntity()
        assertEquals(actor.castId, actorEntity.castId)
        assertEquals(actor.movieId, actorEntity.movieId)
        assertEquals(actor.name, actorEntity.name)
        assertEquals(actor.orderInCredits, actorEntity.orderInCredits)
        assertEquals(actor.character, actorEntity.character)
        assertEquals(actor.picture, actorEntity.picture)
    }


    @Test
    fun `ListMovie to MovieEntity conversion should be correct`() {
        val listMovie = ListMovie(1, "Title", "Poster", 8.5f, 1000, "13+", "2023-01-01", "Genre1, Genre2", false)
        val movieEntity = listMovie.toMovieEntity()
        assertEquals(listMovie.id, movieEntity.id)
        assertEquals(listMovie.title, movieEntity.title)
        assertEquals(listMovie.poster, movieEntity.poster)
        assertEquals(listMovie.ratings, movieEntity.ratings)
        assertEquals(listMovie.numberOfRatings, movieEntity.numberOfRatings)
        assertEquals(listMovie.minimumAge, movieEntity.minimumAge)
        assertEquals(listMovie.releaseDate, movieEntity.releaseDate)
        assertEquals(listMovie.genres, movieEntity.genres)
        assertEquals(listMovie.isFavorite, movieEntity.isFavorite)
    }


    @Test
    fun `GenreDto to GenreEntity conversion should be correct`() {
        val genreDto = GenreDto(1, "Genre")
        val genreEntity = genreDto.toGenreEntity()
        assertEquals(genreDto.id, genreEntity.id)
        assertEquals(genreDto.name, genreEntity.name)
    }

    @Test
    fun `GenreEntity to GenreDto conversion should be correct`() {
        val genreEntity = GenreEntity(1, "Genre")
        val genreDto = genreEntity.toGenreDto()
        assertEquals(genreEntity.id, genreDto.id)
        assertEquals(genreEntity.name, genreDto.name)
    }

    @Test
    fun `toActorsList conversion should be correct`() {
        val actorsDto = listOf(
            CastDto(1, 2, "Name1", 3, "Character1", "Picture1"),
            CastDto(4, 5, "Name2", 2, "Character2", "Picture2")
        )
        val actors = toActorsList(actorsDto, 1, "https://image.tmdb.org/t/p/")
        assertEquals(2, actors.size)
        assertEquals("Name2", actors[0].name)
        assertEquals("Name1", actors[1].name)
        assertEquals("https://image.tmdb.org/t/p/w342/Picture2", actors[0].picture)
        assertEquals("https://image.tmdb.org/t/p/w342/Picture1", actors[1].picture)
    }

    @Test
    fun `normalizedRating function should return correct value`() {
        assertEquals(4.25f, normalizedRating(8.5f))
        assertEquals(0.5f, normalizedRating(1f))
        assertEquals(0.5f, normalizedRating(null))
    }

    @Test
    fun `normalizedMinimumAge function should return correct value`() {
        assertEquals("18+", normalizedMinimumAge(true))
        assertEquals("13+", normalizedMinimumAge(false))
    }



    @Test
    fun `normalizedImgUrl function should return correct value`() {
        assertEquals("https://image.tmdb.org/t/p/w342/Path", normalizedImgUrl("https://image.tmdb.org/t/p/", "Path"))
    }
}
