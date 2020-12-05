package com.zelyder.movie

class MoviesDataSource {
    fun getMovies(): List<Movie> {
        return listOf(
            Movie(
                0, "Avengers: End Game", R.drawable.avengers_card_poster,
                "13+", false, "Action, Adventure, Fantasy", 4f,
                125, 137, R.drawable.avengers_header_poster,
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe."
            ),
            Movie(
                1, "Tenet", R.drawable.tenet_card_poster,
                "16+", true, "Action, Sci-Fi, Thriller", 5f,
                98, 97, R.drawable.tenet_big_poster, "Armed with only one word - Tenet - and fighting for the survival of the entire world, the Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time."
            ),
            Movie(
                2, "Black Widow", R.drawable.black_widow_card_poster,
                "13+", false, "Action, Adventure, Sci-Fi", 4f,
                38, 102, R.drawable.black_widow_big_poster, "Natasha Romanoff, also known as Black Widow, confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises. Pursued by a force that will stop at nothing to bring her down, Natasha must deal with her history as a spy and the broken relationships left in her wake long before she became an Avenger."
            ),
            Movie(
                3, "Wonder Woman 1984", R.drawable.wonder_woman_card_poster,
                "13+", false, "Action, Adventure, Fantasy", 5f,
                74, 120, R.drawable.wonder_woman_big_poster, "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah."
            )
        )
    }
    fun getMovieById(id: Int): Movie?{
        val movies = getMovies()
        for(movie in movies){
            if(movie.id == id){
                return movie
            }
        }
        return null
    }
}