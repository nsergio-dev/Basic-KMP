package com.nsergio.dev.myrickandmortyapp.data.remote.responses.episodes

import kotlinx.serialization.Serializable


@Serializable
data class EpisodeInfoResponse(
    val count: Int,
    val pages: Int
)