package com.nsergio.dev.myrickandmortyapp.data.remote.responses.episodes

import com.nsergio.dev.myrickandmortyapp.domain.model.EpisodeModel
import com.nsergio.dev.myrickandmortyapp.domain.model.SeasonEpisode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class EpisodeItemResponse(
    val id: Int,
    val name: String,
    @SerialName("air_date")
    val date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String,
) {

    fun toDomain(): EpisodeModel {
        val idCharacter = characters.map { url ->
            url.substringAfter("/")
        }

        return EpisodeModel(
            id = id,
            name = name,
            airDate = date,
            episode = episode,
            characters = idCharacter,
            url = url,
            season = getSeasonFromEpisode(episode)
        )

    }

    private fun getSeasonFromEpisode(episode: String):  SeasonEpisode {
        val season = when {
            //episode.startsWith("S01") -> SeasonEpisode.SEASON_1
            episode.contains("S01") -> SeasonEpisode.SEASON_1
            episode.contains("S02") -> SeasonEpisode.SEASON_2
            episode.contains("S03") -> SeasonEpisode.SEASON_3
            episode.contains("S04") -> SeasonEpisode.SEASON_4
            episode.contains("S05") -> SeasonEpisode.SEASON_5
            episode.contains("S06") -> SeasonEpisode.SEASON_6
            episode.contains("S07") -> SeasonEpisode.SEASON_7
            episode.contains("S08") -> SeasonEpisode.SEASON_8
            else -> SeasonEpisode.UNKNOWN
        }

        return season
    }
}