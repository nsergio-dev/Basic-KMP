package com.nsergio.dev.myrickandmortyapp.data.remote.responses.episodes

import kotlinx.serialization.Serializable


@Serializable
data class EpisodeResponse(
    val info: EpisodeInfoResponse,
    val results: List<EpisodeItemResponse> = listOf()
)