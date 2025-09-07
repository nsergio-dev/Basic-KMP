package com.nsergio.dev.myrickandmortyapp.domain.model

import myrickandmortyapp.composeapp.generated.resources.Res
import myrickandmortyapp.composeapp.generated.resources.season1
import myrickandmortyapp.composeapp.generated.resources.season2
import myrickandmortyapp.composeapp.generated.resources.season3
import myrickandmortyapp.composeapp.generated.resources.season4
import myrickandmortyapp.composeapp.generated.resources.season5
import myrickandmortyapp.composeapp.generated.resources.season6
import myrickandmortyapp.composeapp.generated.resources.season7
import myrickandmortyapp.composeapp.generated.resources.season8
import org.jetbrains.compose.resources.DrawableResource

data class EpisodeModel(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String = "",
    val season: SeasonEpisode
)

enum class SeasonEpisode {

    SEASON_1 {
        override val trailerUrl: String =
            "https://www.youtube.com/watch?v=8BEzj2kRjO8&ab_channel=RottenTomatoesTV"
        override val icon: DrawableResource = Res.drawable.season1
    },
    SEASON_2 {
        override val trailerUrl: String =
            "https://www.youtube.com/watch?v=SXwf_9xJu5c&ab_channel=Yusuto"

        override val icon: DrawableResource = Res.drawable.season2
    },
    SEASON_3 {
        override val trailerUrl: String =
            "https://www.youtube.com/watch?v=Bmg2vXOQ3kM&ab_channel=SeriesTrailerMP"

        override val icon: DrawableResource = Res.drawable.season3
    },
    SEASON_4 {
        override val trailerUrl: String =
            "https://www.youtube.com/watch?v=bLI2-v264No&ab_channel=RottenTomatoesTV"

        override val icon: DrawableResource = Res.drawable.season4
    },
    SEASON_5 {
        override val trailerUrl: String =
            "https://www.youtube.com/watch?v=yC1UxW8vcDo&ab_channel=RottenTomatoesTV"

        override val icon: DrawableResource = Res.drawable.season5
    },
    SEASON_6 {
        override val trailerUrl: String =
            "https://www.youtube.com/watch?v=jerFRSQW9g8&ab_channel=RottenTomatoesTV"

        override val icon: DrawableResource = Res.drawable.season6
    },
    SEASON_7 {
        override val trailerUrl: String =
            "https://www.youtube.com/watch?v=PkZtVBNkmso&ab_channel=RottenTomatoesTV"
        override val icon: DrawableResource = Res.drawable.season7
    },
    SEASON_8 {
        override val trailerUrl: String = "https://www.youtube.com/watch?v=ySYnTO7leqI"
        override val icon: DrawableResource = Res.drawable.season8
    },
    UNKNOWN {
        override val trailerUrl: String = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        override val icon: DrawableResource = Res.drawable.season1
    };

    abstract val trailerUrl: String
    abstract val icon: DrawableResource
}