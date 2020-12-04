package com.zelyder.movie

class MoviesDataSource {
    fun getMovies(): List<Movie>{
        return listOf(
            Movie(0, "Avengers: End Game", R.drawable.avengers_card_poster,
                "13+", false, "Action, Adventure, Fantasy", 4f,
            125,137),
            Movie(1, "Tenet", R.drawable.tenet_card_poster,
                "16+", true, "Action, Sci-Fi, Thriller", 5f,
                98,97),
            Movie(2, "Black Widow", R.drawable.black_widow_card_poster,
                "13+", false, "Action, Adventure, Sci-Fi", 4f,
                38,102),
            Movie(3, "Wonder Woman 1984", R.drawable.wonder_woman_card_poster,
                "13+", false, "Action, Adventure, Fantasy", 5f,
                74,120)
        )
    }
}