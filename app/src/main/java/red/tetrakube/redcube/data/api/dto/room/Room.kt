package red.tetrakube.redcube.data.api.dto.room

import kotlinx.serialization.Serializable

@Serializable
class Room(
    val slug: String,
    val name: String
)